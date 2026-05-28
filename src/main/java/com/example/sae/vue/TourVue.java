package com.example.sae.vue;

import com.example.sae.modele.Tour;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TourVue {

    public TourVue(Tour tour, Pane paneJeu, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(32);
        imageView.setFitHeight(32);
        imageView.setLayoutX(tour.getPixelX());
        imageView.setLayoutY(tour.getPixelY());
        paneJeu.getChildren().add(imageView);
    }
}
