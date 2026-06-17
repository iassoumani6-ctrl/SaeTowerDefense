package com.example.sae.vue.defenseursVue;

import com.example.sae.modele.Tour;
import com.example.sae.modele.defenseurs.Shifty;
import com.example.sae.modele.defenseurs.Zoner;
import com.example.sae.vue.TourSimpleVue;
import com.example.sae.vue.TourVue;
import javafx.scene.layout.Pane;

public class TourVueFactory {

    public static TourVue creerTourVue(Tour tour, Pane paneJeu) {
        if (tour instanceof Shifty) {
            return new ShiftyVue((Shifty) tour, paneJeu);
        }

        if (tour instanceof Zoner) {
            return new ZonerVue((Zoner) tour, paneJeu);
        }

        return new TourSimpleVue(tour, paneJeu);
    }
}