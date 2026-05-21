package com.example.sae;

import com.example.sae.modele.Terrain;
import com.example.sae.vue.TerrainVue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
<<<<<<< HEAD
import javafx.scene.image.ImageView;
=======
import javafx.scene.layout.Pane;
>>>>>>> 9112d81 (Refonte du deplacement de l'attaquant pour le faire deplacer sur les)
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    @FXML
    private TilePane tilepane;
    @FXML
    private Pane paneJeu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Terrain terrain = new Terrain();
        TerrainVue terrainVue = new TerrainVue(terrain, tilepane);
        terrainVue.dessinerTerrain();

<<<<<<< HEAD






=======
        Attaquant attaquant = new Attaquant();

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
>>>>>>> 9112d81 (Refonte du deplacement de l'attaquant pour le faire deplacer sur les)

    }
}
