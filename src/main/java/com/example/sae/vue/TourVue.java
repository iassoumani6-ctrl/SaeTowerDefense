package com.example.sae.vue;

import com.example.sae.modele.Tour;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TourVue {

    public TourVue(Tour tour, Pane paneJeu) {
        Image image = new Image(getClass().getResourceAsStream(tour.getCheminImage()));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(Tour.TAILLE_CASES * 32);
        imageView.setFitHeight(Tour.TAILLE_CASES * 32);

        imageView.setLayoutX(tour.getPixelX());
        imageView.setLayoutY(tour.getPixelY());

        paneJeu.getChildren().add(imageView);
    }
}