package com.example.sae;

import com.example.sae.modele.Attaquant;
import com.example.sae.modele.Terrain;
import com.example.sae.modele.Tour;
import com.example.sae.modele.ennemis.EnnemieVert;
import com.example.sae.vue.AttaquantVue;
import com.example.sae.vue.TerrainVue;
import com.example.sae.vue.TourVue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    @FXML private Pane paneJeu;

    private Terrain terrain;
    private Image imageTour;
    private Image imageAttaquant;

    private final List<Tour>         tours         = new ArrayList<>();
    private final List<AttaquantVue> attaquantVues = new ArrayList<>();
    private final Random random = new Random();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        terrain = new Terrain();

        new TerrainVue(terrain, paneJeu).dessinerTerrain();

        imageTour      = new Image(Main.class.getResourceAsStream("/com/example/sae/image/tour.png"));
        imageAttaquant = new Image(Main.class.getResourceAsStream("/com/example/sae/image/Ballon.png"));

        ajouterEnnemi(new EnnemieVert());

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> tick())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // ------------------------------------------------------------------ //
    //  Boucle de jeu                                                       //
    // ------------------------------------------------------------------ //

    private void tick() {
        // 1. Déplacer tous les ennemis
        for (AttaquantVue av : attaquantVues) {
            av.getAttaquant().avancer();
            av.mettreAJourPosition();
        }

        // 2. Chaque tour tire sur le premier ennemi à portée
        for (Tour tour : tours) {
            for (AttaquantVue av : attaquantVues) {
                if (tour.tirerSur(av.getAttaquant())) {
                    break; // une cible à la fois par tour
                }
            }
        }

        // 3. Supprimer les ennemis morts
        Iterator<AttaquantVue> it = attaquantVues.iterator();
        while (it.hasNext()) {
            AttaquantVue av = it.next();
            if (av.getAttaquant().estMort()) {
                av.supprimer();
                it.remove();
            }
        }
    }

    // ------------------------------------------------------------------ //
    //  Helpers                                                             //
    // ------------------------------------------------------------------ //

    private void ajouterEnnemi(Attaquant ennemi) {
        AttaquantVue av = new AttaquantVue(ennemi, paneJeu, imageAttaquant);
        av.mettreAJourPosition();
        attaquantVues.add(av);
    }

    // ------------------------------------------------------------------ //
    //  Bouton FXML                                                         //
    // ------------------------------------------------------------------ //

    @FXML
    private void ajouterTourAleatoire() {
        int col, ligne;
        int tentatives = 0;

        // Cherche une case non praticable (= herbe, pas le chemin)
        do {
            col   = random.nextInt(terrain.getLargeurGrille());
            ligne = random.nextInt(terrain.getHauteurGrille());
            tentatives++;
        } while (terrain.estPraticable(ligne, col) && tentatives < 200);

        if (tentatives < 200) {
            Tour tour = new Tour(col, ligne);
            tours.add(tour);
            new TourVue(tour, paneJeu, imageTour);
        }
    }
}
