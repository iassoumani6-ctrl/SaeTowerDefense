package com.example.sae;

import com.example.sae.modele.Attaquant;
import com.example.sae.modele.Terrain;
import com.example.sae.modele.ennemis.EnnemieVert;
import com.example.sae.vue.AttaquantVue;
import com.example.sae.vue.TerrainVue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    @FXML
    private Pane paneJeu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Terrain terrain = new Terrain();

        TerrainVue terrainVue = new TerrainVue(terrain, paneJeu);
        terrainVue.dessinerTerrain();

        Attaquant attaquant = new EnnemieVert();

        Image imageAttaquant = new Image(Main.class.getResourceAsStream("/com/example/sae/image/Ballon.png"));
        AttaquantVue attaquantVue = new AttaquantVue(attaquant, paneJeu, imageAttaquant);
        attaquantVue.mettreAJourPosition();

        // Déplacer d'une case aléatoire toutes les 1 seconde
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.010), event -> {
                    attaquant.avancer();
                    attaquantVue.mettreAJourPosition();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }
}
