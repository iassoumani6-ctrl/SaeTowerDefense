package com.example.sae.vue;

import com.example.sae.modele.Tour;
import javafx.scene.layout.Pane;

public class TourSimpleVue extends TourVue {

    public TourSimpleVue(Tour tour, Pane paneJeu) {
        super(tour, paneJeu, tour.getCheminImage());
    }
}