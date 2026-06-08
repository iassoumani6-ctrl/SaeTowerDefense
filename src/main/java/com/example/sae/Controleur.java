package com.example.sae;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Terrain;
import com.example.sae.modele.Tour;
import com.example.sae.modele.defenseurs.Shifty;
import com.example.sae.modele.defenseurs.Zoner;
import com.example.sae.modele.ennemis.*;
import com.example.sae.vue.BallonVue;
import com.example.sae.vue.TerrainVue;
import com.example.sae.vue.TourVue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import com.example.sae.modele.defenseurs.Laser;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    @FXML private Pane  paneJeu;
    @FXML private Label selectionLabel;

    private Terrain terrain;
    private Image   imageTour;
    private TerrainVue terrainVue;

    private final List<Tour>         tours         = new ArrayList<>();
    private final List<BallonVue> ballonVues = new ArrayList<>();


    private int colSelectionnee  = -1; // -1 = aucune case selectionnée
    private int ligneSelectionnee = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        terrain = new Terrain();
        terrainVue = new TerrainVue(terrain, paneJeu);
        terrainVue.dessinerTerrain();

        imageTour      = new Image(Main.class.getResourceAsStream("/com/example/sae/image/Laser.png"));

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
        for (BallonVue av : ballonVues) {
            av.getBallon().avancer();

        }

        for (Tour tour : tours) {
            for (BallonVue av : ballonVues) {
                if (tour.tirerSur(av.getBallon())) {
                    break;
                }
            }
        }

        Iterator<BallonVue> it = ballonVues.iterator();//Iterator sert ici à parcourir la liste des ballons et à
        // pouvoir supprimer proprement ceux qui sont morts pendant la boucle.
        while (it.hasNext()) {
            BallonVue av = it.next();
            if (av.getBallon().estMort()) {
                av.supprimer();
                it.remove();
            }
        }
    }


    private void ajouterEnnemi(Ballon ennemi) {
        Image imageEnnemi = new Image(Main.class.getResourceAsStream(ennemi.getCheminImage()));

        BallonVue av = new BallonVue(ennemi, paneJeu, imageEnnemi);
        ballonVues.add(av);
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
    private void ajouterTour() {
        if (colSelectionnee == -1 || ligneSelectionnee == -1) {
            System.out.println("Sélectionnez une case valide d'abord !");
            return;
        }

        if (!terrain.peutPlacerTour(ligneSelectionnee, colSelectionnee, Tour.TAILLE_CASES)) {
            System.out.println("Impossible de placer la tour ici !");
            return;
        }

        Tour tour = new Zoner(colSelectionnee, ligneSelectionnee);

        terrain.occuperCasesTour(tour);
        tours.add(tour);

        new TourVue(tour, paneJeu);

        colSelectionnee = -1;
        ligneSelectionnee = -1;

        terrainVue.cacherSelection();

        System.out.println("Tour placée");
    }
}
