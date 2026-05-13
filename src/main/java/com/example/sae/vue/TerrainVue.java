package com.example.sae.vue;

import com.example.sae.Main;
import com.example.sae.modele.Terrain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class TerrainVue {

    private Terrain terrain;
    private TilePane tilePane;

    public TerrainVue(Terrain terrain, TilePane tilePane) {
        this.terrain = terrain;
        this.tilePane = tilePane;
//        this.tilePane.setPrefColumns(terrain.largeur());
    }

    public void dessinerTerrain() {
        Image bleu = new Image(Main.class.getResourceAsStream("bleu.png"));
        Image beige = new Image(Main.class.getResourceAsStream("beige.png"));

        for (int ligne = 0; ligne < terrain.hauteur(); ligne++) {
            for (int col = 0; col < terrain.largeur(); col++) {
                switch (terrain.codeTuile(ligne, col)){
                    case 1: tilePane.getChildren().add(new ImageView(bleu)); break;
                    case 2: tilePane.getChildren().add(new ImageView(beige)); break;
                }

            }
        }

    }

}
