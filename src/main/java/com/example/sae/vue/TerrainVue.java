package com.example.sae.vue;

import com.example.sae.Main;
import com.example.sae.modele.Terrain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TerrainVue {

    private Terrain terrain;
    private Pane paneJeu;
    private ImageView imageMap;
    private Rectangle selectionCase;

    public TerrainVue(Terrain terrain, Pane paneJeu) {
        this.terrain = terrain;
        this.paneJeu = paneJeu;

        Image image = new Image(Main.class.getResourceAsStream("/com/example/sae/image/TerrainBasket.png"));

        this.imageMap = new ImageView(image);
        this.imageMap.setFitWidth(terrain.getLargeurPixels());
        this.imageMap.setFitHeight(terrain.getHauteurPixels());

        this.paneJeu.setPrefWidth(terrain.getLargeurPixels());
        this.paneJeu.setPrefHeight(terrain.getHauteurPixels());

        this.selectionCase = new Rectangle(Terrain.TAILLE_CASE * 2, Terrain.TAILLE_CASE * 2);
        this.selectionCase.setStrokeWidth(2);
        this.selectionCase.setVisible(false);

        this.paneJeu.getChildren().addAll(this.imageMap, this.selectionCase);
    }

    public void dessinerTerrain() {
        this.imageMap.toBack();
    }

    public void afficherSelection(int ligne, int colonne, boolean valide) {
        if (valide) {
            selectionCase.setFill(Color.rgb(255, 255, 0, 0.4));
            selectionCase.setStroke(Color.YELLOW);
        } else {
            selectionCase.setFill(Color.rgb(255, 0, 0, 0.4));
            selectionCase.setStroke(Color.RED);
        }

        selectionCase.setLayoutX(colonne * Terrain.TAILLE_CASE);
        selectionCase.setLayoutY(ligne * Terrain.TAILLE_CASE);
        selectionCase.setVisible(true);
        selectionCase.toFront();
    }

    public void cacherSelection() {
        selectionCase.setVisible(false);
    }
}