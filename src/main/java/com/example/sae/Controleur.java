package com.example.sae;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Terrain;
import com.example.sae.modele.Tour;
import com.example.sae.modele.ennemis.BallonJaune;
import com.example.sae.modele.ennemis.BallonOrange;
import com.example.sae.modele.ennemis.BallonRouge;
import com.example.sae.modele.ennemis.BallonVert;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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


    private final List<Tour>         tours         = new ArrayList<>();
    private final List<BallonVue> ballonVues = new ArrayList<>();


    private int colSelectionnee  = -1; // -1 = aucune case selectionnée
    private int ligneSelectionnee = -1;
    private Rectangle highlightCase;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        terrain = new Terrain();
        new TerrainVue(terrain, paneJeu).dessinerTerrain();

        imageTour      = new Image(Main.class.getResourceAsStream("/com/example/sae/image/tour.png"));

        ajouterEnnemi(new BallonVert());

        // Création de l'indicateur visuel (rectangle semi-transparent)
        highlightCase = new Rectangle(Terrain.TAILLE_CASE, Terrain.TAILLE_CASE);
        highlightCase.setFill(Color.rgb(255, 255, 0, 0.4));
        highlightCase.setStroke(Color.YELLOW);
        highlightCase.setStrokeWidth(2);
        highlightCase.setVisible(false);
        paneJeu.getChildren().add(highlightCase);

        // Écoute des clics sur le pane de jeu
        paneJeu.setOnMouseClicked(event -> gererClicSurTerrain(event.getX(), event.getY()));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10), e -> tick())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void gererClicSurTerrain(double pixelX, double pixelY) {
        int col   = (int) (pixelX / Terrain.TAILLE_CASE);
        int ligne = (int) (pixelY / Terrain.TAILLE_CASE);

        if (col < 0 || col >= terrain.getLargeurGrille()
                || ligne < 0 || ligne >= terrain.getHauteurGrille()) {
            return;
        }

        if (terrain.estPraticable(ligne, col)) {
            // Case invalide (chemin) : feedback rouge
            highlightCase.setFill(Color.rgb(255, 0, 0, 0.4));
            highlightCase.setStroke(Color.RED);
            colSelectionnee   = -1;
            ligneSelectionnee = -1;
            if (selectionLabel != null) {
                System.out.println("Chemin invalide !!!!!");
                selectionLabel.setStyle("-fx-text-fill: red;");
            }
        } else {
            highlightCase.setFill(Color.rgb(255, 255, 0, 0.4));
            highlightCase.setStroke(Color.YELLOW);
            colSelectionnee   = col;
            ligneSelectionnee = ligne;
            if (selectionLabel != null) {
                System.out.println("Case sélectionnée = " + ligne + " " + col);
                selectionLabel.setStyle("-fx-text-fill: green;");
            }
        }

        // Positionner et afficher le rectangle de surbrillance
        highlightCase.setLayoutX(col   * Terrain.TAILLE_CASE);
        highlightCase.setLayoutY(ligne * Terrain.TAILLE_CASE);
        highlightCase.setVisible(true);
        highlightCase.toFront();
    }

    private void tick() {
        for (BallonVue av : ballonVues) {
            av.getBallon().avancer();
            av.mettreAJourPosition();
        }

        for (Tour tour : tours) {
            for (BallonVue av : ballonVues) {
                if (tour.tirerSur(av.getBallon())) {
                    break;
                }
            }
        }

        Iterator<BallonVue> it = ballonVues.iterator();
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
        av.mettreAJourPosition();
        ballonVues.add(av);
    }


    @FXML
    private void spawnerEnnemi() {
        Ballon[] types = {
            new BallonVert(),
            new BallonRouge(),
            new BallonJaune(),
            new BallonOrange()
        };
        Ballon ennemi = types[(int)(Math.random() * types.length)];
        ajouterEnnemi(ennemi);
    }

    @FXML
    private void ajouterTour() {
        if (colSelectionnee == -1 || ligneSelectionnee == -1) {
            if (selectionLabel != null) {
                System.out.println("sélectionnez une case d'abord !!");
            }
            return;
        }

        Tour tour = new Tour(colSelectionnee, ligneSelectionnee);
        tours.add(tour);
        new TourVue(tour, paneJeu, imageTour);

        //reset la sélection après placement
        colSelectionnee   = -1;
        ligneSelectionnee = -1;
        highlightCase.setVisible(false);
        if (selectionLabel != null) {
            System.out.println("tour placée");
        }
    }
}
