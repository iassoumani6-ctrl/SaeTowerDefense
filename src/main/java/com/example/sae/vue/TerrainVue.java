package com.example.sae.vue;

import com.example.sae.Main;
import com.example.sae.modele.Terrain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.Pane;

public class TerrainVue {

    private Terrain terrain;
    private Pane paneJeu;
    private ImageView imageMap;

    public TerrainVue(Terrain terrain, Pane paneJeu) {
        this.terrain = terrain;
        this.paneJeu = paneJeu;

        Image image = new Image(Main.class.getResourceAsStream("/com/example/sae/image/TerrainBasket.png"));

        this.imageMap = new ImageView(image);

        this.imageMap.setFitWidth(terrain.getLargeurPixels());
        this.imageMap.setFitHeight(terrain.getHauteurPixels());

        this.paneJeu.setPrefWidth(terrain.getLargeurPixels());
        this.paneJeu.setPrefHeight(terrain.getHauteurPixels());

        this.paneJeu.getChildren().add(this.imageMap);
    }

    public void dessinerTerrain() {
        this.imageMap.toBack();
    }
}

