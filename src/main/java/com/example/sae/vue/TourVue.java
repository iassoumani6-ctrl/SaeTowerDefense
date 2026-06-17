package com.example.sae.vue;

import com.example.sae.modele.Tour;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class TourVue {

    protected Tour tour;
    protected ImageView imageView;

    public TourVue(Tour tour, Pane paneJeu, String cheminImage) {
        this.tour = tour;

        Image image = new Image(getClass().getResourceAsStream(cheminImage));
        this.imageView = new ImageView(image);

        imageView.setFitWidth(Tour.TAILLE_CASES * 32);
        imageView.setFitHeight(Tour.TAILLE_CASES * 32);

        imageView.setLayoutX(tour.getPixelX());
        imageView.setLayoutY(tour.getPixelY());

        paneJeu.getChildren().add(imageView);
    }

    public Tour getTour() {
        return tour;
    }
}