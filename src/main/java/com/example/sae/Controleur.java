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
import com.example.sae.modele.Partie;
import com.example.sae.vue.PartieVue;
import javafx.scene.image.ImageView;
public class Controleur implements Initializable {

    @FXML private Pane  paneJeu;
    @FXML private Label selectionLabel;
    @FXML private Button boutonVitesseJeu;
    @FXML private Label piecesLabel;
    @FXML private Label vagueLabel;
    @FXML private ImageView coeursVieImageView;
    @FXML private ImageView coinImageView;

    private Terrain terrain;
    private TerrainVue terrainVue;
    private GestionnaireVagues gestionnaireVagues;
    private Partie partie;
    private PartieVue partieVue;

    private Timeline timeline;
    private final double[] vitessesJeu = {1.0, 2.0, 2.5};
    private int indiceVitesseJeu = 0;

    private final List<Tour>         tours         = new ArrayList<>();
    private final ObservableList<Ballon> ballons = FXCollections.observableArrayList();
    private final Map<Ballon, BallonVue> ballonVueMap = new HashMap<>();

    private int compteurGainArgent = 0;
    private static final int tickGainArgent = 300;
    private static final int argentPassif = 5;

    private int colSelectionnee  = -1; // -1 = aucune case selectionnée
    private int ligneSelectionnee = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        terrain = new Terrain();
        terrainVue = new TerrainVue(terrain, paneJeu);
        terrainVue.dessinerTerrain();
        partie = new Partie(150, 100);
        partieVue = new PartieVue(partie, coeursVieImageView, coinImageView, piecesLabel);

        ballons.addListener(new BallonsListener(paneJeu, ballonVueMap));


        gestionnaireVagues = new GestionnaireVagues();


        ajouterEnnemi(new BallonVert());

        paneJeu.setOnMouseClicked(event -> gererClicSurTerrain(event.getX(), event.getY()));

        this.timeline = new Timeline(
                new KeyFrame(Duration.millis(20 ), e -> tick())
        );
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.setRate(vitessesJeu[indiceVitesseJeu]);
        this.timeline.play();
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

        compteurGainArgent++;

        if (compteurGainArgent >= tickGainArgent) {
            partie.gagnerArgent(argentPassif);
            compteurGainArgent = 0;
        }

        Ballon ballonSpawn = gestionnaireVagues.tick();

        if (ballonSpawn != null) {
            ajouterEnnemi(ballonSpawn);
        }

        if (vagueLabel != null) {
            vagueLabel.setText(String.valueOf(gestionnaireVagues.getNumeroVague()));
        }

        for (Ballon iBallon : ballons) {
            iBallon.avancer();

        }

        // Chaque tour reçoit la liste complète des ballons : indispensable
        // pour les tours de zone (Zoner, Canonner) et à verrouillage (Laser,
        // Shifty). Chaque tour applique son propre comportement d'attaque.
        for (Tour tour : tours) {
            tour.attaquer(ballons);
        }

        Iterator<Ballon> itB = ballons.iterator();//Iterator sert a parcourir une liste

        while (itB.hasNext()) {
            Ballon ballon = itB.next();

            if (ballon.estMort()) {
                partie.gagnerArgent(ballon.getRecompense());
                itB.remove();
            } else if (ballon.estArrivee()) {
                partie.perdrePv(ballon.getDegats());
                itB.remove();
            }
        }

        if (gestionnaireVagues.isVagueEnCours() && gestionnaireVagues.isFileSpawnVide() && ballons.isEmpty()) {

            gestionnaireVagues.signalerVagueFinie();
        }

        if (partie.partiePerdue()) {
            afficherDefaite();
            timeline.stop();
        }
    }

    private void afficherDefaite() {
        System.out.println("PERDU");
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
            message("Sélectionnez une case valide d'abord !");
            return;
        }

        if (!terrain.peutPlacerTour(ligneSelectionnee, colSelectionnee, Tour.TAILLE_CASES)) {
            message("Impossible de placer la tour ici !");
            return;
        }

        String type = ((Button) event.getSource()).getText();

        int cout = coutTour(type);
        int vagueDeblocage = vagueDeblocageTour(type);

        // Déblocage par vague
        if (gestionnaireVagues.getNumeroVague() < vagueDeblocage) {
            message(type + " : débloqué à la vague " + vagueDeblocage);
            return;
        }

        // Coût en pièces
        if (!partie.peutPayer(cout)) {
            message(type + " : pas assez de pièces (" + cout + " requis)");
            return;
        }

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

        partie.depenserArgent(cout);

        colSelectionnee = -1;
        ligneSelectionnee = -1;

        terrainVue.cacherSelection();

        message(type + " placé(e) !");
    }

    /** Coût en pièces de chaque type de tour. */
    private int coutTour(String type) {
        return switch (type) {
            case "Shooter"      -> 150;
            case "Laser"        -> 200;
            case "Zoner"        -> 300;
            case "Ralentisseur" -> 350;
            case "Canonner"     -> 500;
            default             -> 100; // Shifty
        };
    }

    /** Vague à partir de laquelle chaque type de tour est débloqué. */
    private int vagueDeblocageTour(String type) {
        return switch (type) {
            case "Laser"        -> 2;
            case "Zoner"        -> 3;
            case "Ralentisseur" -> 4;
            case "Canonner"     -> 5;
            default             -> 1; // Shifty et Shooter
        };
    }

    private void message(String texte) {
        if (selectionLabel != null) {
            selectionLabel.setText(texte);
        }
        System.out.println(texte);
    }

    @FXML
    private void changerVitesseJeu() {
        indiceVitesseJeu++;

        if (indiceVitesseJeu >= vitessesJeu.length) {
            indiceVitesseJeu = 0;
        }

        double nouvelleVitesse = vitessesJeu[indiceVitesseJeu];

        timeline.setRate(nouvelleVitesse);

        if (nouvelleVitesse == 1.0 || nouvelleVitesse == 2.0) {
            boutonVitesseJeu.setText("x" + (int) nouvelleVitesse);
        } else {
            boutonVitesseJeu.setText("x" + nouvelleVitesse);
        }

        System.out.println("Vitesse du jeu : x" + nouvelleVitesse);
    }
}
