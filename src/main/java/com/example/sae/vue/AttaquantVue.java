package com.example.sae.vue;

import com.example.sae.modele.Attaquant;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class AttaquantVue {

    private Attaquant attaquant;
    private ImageView imageView;
    private TilePane tilepane;
    private int largeurTerrain; // nombre de colonnes

    public AttaquantVue(Attaquant attaquant, TilePane tilepane, Image image, int largeurTerrain) {
        this.attaquant = attaquant;
        this.tilepane = tilepane;
        this.largeurTerrain = largeurTerrain;

        this.imageView = new ImageView(image);
        tilepane.getChildren().add(imageView);

    }

    public void mettreAJourPosition() {
        int i = attaquant.getLigne() * largeurTerrain + attaquant.getColonne();
        tilepane.getChildren().remove(imageView);
        tilepane.getChildren().add(i, imageView);
    }
}
