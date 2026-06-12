package com.example.sae;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Terrain;
import com.example.sae.modele.Tour;
import com.example.sae.modele.defenseurs.*;
import com.example.sae.modele.ennemis.*;
import com.example.sae.vue.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.HashMap;
import java.util.Map;
import com.example.sae.vue.listener.BallonsListener;
import com.example.sae.modele.GestionnaireVagues;

public class Controleur implements Initializable {

    @FXML private Pane  paneJeu;
    @FXML private Label selectionLabel;

    private Terrain terrain;
    private Image   imageTour;
    private TerrainVue terrainVue;
    private GestionnaireVagues gestionnaireVagues;

    private final List<Tour>                 tours           = new ArrayList<>();
    private final ObservableList<Ballon>     ballons         = FXCollections.observableArrayList();
    private final Map<Ballon, BallonVue>     ballonVueMap    = new HashMap<>();
    private final Map<Laser, LaserVue>       laserVueMap     = new HashMap<>();
    private final Map<Shooter, ShooterVue>   shooterVueMap   = new HashMap<>();
    private final Map<Canonner, CanonnerVue> canonnerVueMap  = new HashMap<>();

    private int colSelectionnee   = -1;
    private int ligneSelectionnee = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        terrain    = new Terrain();
        terrainVue = new TerrainVue(terrain, paneJeu);
        terrainVue.dessinerTerrain();

        ballons.addListener(new BallonsListener(paneJeu, ballonVueMap));

        gestionnaireVagues = new GestionnaireVagues();

        imageTour = new Image(Main.class.getResourceAsStream("/com/example/sae/image/attaque_tour/rayon_laser.png"));

        ajouterEnnemi(new BallonVert());

        paneJeu.setOnMouseClicked(event -> gererClicSurTerrain(event.getX(), event.getY()));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10), e -> tick())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void gererClicSurTerrain(double pixelX, double pixelY) {
        int colonne = (int) (pixelX / Terrain.TAILLE_CASE);
        int ligne   = (int) (pixelY / Terrain.TAILLE_CASE);

        boolean valide = terrain.peutPlacerTour(ligne, colonne, Tour.TAILLE_CASES);

        terrainVue.afficherSelection(ligne, colonne, valide);

        if (valide) {
            colSelectionnee   = colonne;
            ligneSelectionnee = ligne;
            System.out.println("Case sélectionnée = " + ligne + " " + colonne);
        } else {
            colSelectionnee   = -1;
            ligneSelectionnee = -1;
            System.out.println("Placement invalide");
        }
    }

    private void tick() {

        Ballon ballonSpawn = gestionnaireVagues.tick();
        if (ballonSpawn != null) {
            ajouterEnnemi(ballonSpawn);
        }

        for (Ballon iBallon : ballons) {
            iBallon.avancer();
        }

        for (Tour tour : tours) {
            for (Ballon iBallon : ballons) {
                if (tour.tirerSur(iBallon)) {
                    break;
                }
            }
        }

        // Mise à jour logique + visuelle des lasers
        for (Map.Entry<Laser, LaserVue> entry : laserVueMap.entrySet()) {
            entry.getKey().update();
            entry.getValue().update();
        }

        // Mise à jour logique + visuelle des shooters
        for (Map.Entry<Shooter, ShooterVue> entry : shooterVueMap.entrySet()) {
            entry.getKey().update();
            entry.getValue().update();
        }

        // Mise à jour logique + visuelle des canonniers
        for (Map.Entry<Canonner, CanonnerVue> entry : canonnerVueMap.entrySet()) {
            entry.getKey().update();
            entry.getValue().update();
        }

        Iterator<Ballon> itB = ballons.iterator();
        while (itB.hasNext()) {
            Ballon ballon = itB.next();
            if (ballon.estMort()) {
                itB.remove();
            }
        }

        if (gestionnaireVagues.isVagueEnCours() && gestionnaireVagues.isFileSpawnVide() && ballons.isEmpty()) {
            gestionnaireVagues.signalerVagueFinie();
        }
    }

    private void ajouterEnnemi(Ballon iB) {
        ballons.add(iB);
    }

    @FXML
    private void spawnerEnnemi() {
        Ballon[] types = {
                new BallonVert(),
                new BallonRouge(),
                new BallonJaune(),
                new BallonOrange(),
                new MegaBallon()
        };
        Ballon ennemi = types[(int)(Math.random() * types.length)];
        ajouterEnnemi(ennemi);
    }

    @FXML
    private void ajouterTour(ActionEvent event) {
        if (colSelectionnee == -1 || ligneSelectionnee == -1) {
            System.out.println("Sélectionnez une case valide d'abord !");
            return;
        }

        if (!terrain.peutPlacerTour(ligneSelectionnee, colSelectionnee, Tour.TAILLE_CASES)) {
            System.out.println("Impossible de placer la tour ici !");
            return;
        }

        String type = ((Button) event.getSource()).getText();

        Tour tour = switch (type) {
            case "Shooter"      -> new Shooter(colSelectionnee, ligneSelectionnee);
            case "Zoner"        -> new Zoner(colSelectionnee, ligneSelectionnee);
            case "Ralentisseur" -> new Ralentisseur(colSelectionnee, ligneSelectionnee);
            case "Laser"        -> new Laser(colSelectionnee, ligneSelectionnee);
            case "Canonner"     -> new Canonner(colSelectionnee, ligneSelectionnee);
            default             -> new Shifty(colSelectionnee, ligneSelectionnee);
        };

        terrain.occuperCasesTour(tour);
        tours.add(tour);

        if (tour instanceof Laser laser) {
            LaserVue laserVue = new LaserVue(laser, paneJeu);
            laserVueMap.put(laser, laserVue);
        } else if (tour instanceof Shooter shooter) {
            ShooterVue shooterVue = new ShooterVue(shooter, paneJeu);
            shooterVueMap.put(shooter, shooterVue);
        } else if (tour instanceof Canonner canonner) {
            CanonnerVue canonnerVue = new CanonnerVue(canonner, paneJeu);
            canonnerVueMap.put(canonner, canonnerVue);
        } else {
            new TourVue(tour, paneJeu);
        }

        colSelectionnee   = -1;
        ligneSelectionnee = -1;
        terrainVue.cacherSelection();
        System.out.println("Tour placée");
    }
}