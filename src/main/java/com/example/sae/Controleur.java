package com.example.sae;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Terrain;
import com.example.sae.modele.Tour;
import com.example.sae.modele.defenseurs.*;
import com.example.sae.modele.ennemis.*;
import com.example.sae.vue.BallonVue;
import com.example.sae.vue.TerrainVue;
import com.example.sae.vue.TourVue;
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

public class Controleur implements Initializable {

    @FXML private Pane  paneJeu;
    @FXML private Label selectionLabel;

    private Terrain terrain;
    private Image   imageTour;
    private TerrainVue terrainVue;

    private final List<Tour>         tours         = new ArrayList<>();
    private final ObservableList<Ballon> ballons = FXCollections.observableArrayList();
    private final Map<Ballon, BallonVue> ballonVueMap = new HashMap<>();


    private int colSelectionnee  = -1; // -1 = aucune case selectionnée
    private int ligneSelectionnee = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        terrain = new Terrain();
        terrainVue = new TerrainVue(terrain, paneJeu);
        terrainVue.dessinerTerrain();

        ballons.addListener(new BallonsListener(paneJeu, ballonVueMap));
//
//        imageTour      = new Image(Main.class.getResourceAsStream("/com/example/sae/image/Laser.png"));
//
//        ajouterEnnemi(new BallonVert());

        paneJeu.setOnMouseClicked(event -> gererClicSurTerrain(event.getX(), event.getY()));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10), e -> tick())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void gererClicSurTerrain(double pixelX, double pixelY) {
        int colonne = (int) (pixelX / Terrain.TAILLE_CASE);
        int ligne = (int) (pixelY / Terrain.TAILLE_CASE);

        boolean valide = terrain.peutPlacerTour(ligne, colonne, Tour.TAILLE_CASES);

        terrainVue.afficherSelection(ligne, colonne, valide);

        if (valide) {
            colSelectionnee = colonne;
            ligneSelectionnee = ligne;
            System.out.println("Case sélectionnée = " + ligne + " " + colonne);
        } else {
            colSelectionnee = -1;
            ligneSelectionnee = -1;
            System.out.println("Placement invalide");
        }
    }

    private void tick() {
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

        Iterator<Ballon> itB = ballons.iterator();//Iterator sert a parcourir une liste

        while (itB.hasNext()) {
            Ballon ballon = itB.next();

            if (ballon.estMort()) {
                itB.remove(); // on supprime le Ballon de la liste
            }
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

        new TourVue(tour, paneJeu);

        colSelectionnee = -1;
        ligneSelectionnee = -1;

        terrainVue.cacherSelection();

        System.out.println("Tour placée");
    }
}
