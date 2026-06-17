package com.example.sae.vue.defenseursVue;

import com.example.sae.modele.defenseurs.Shifty;
import com.example.sae.vue.TourVue;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class ShiftyVue extends TourVue {

    private Shifty shifty;
    private Image[] images;

    public ShiftyVue(Shifty shifty, Pane paneJeu) {
        super(shifty, paneJeu, "/com/example/sae/image/ShiftyAnim/Shifty-1.png");

        this.shifty = shifty;
        this.images = new Image[14];

        for (int i = 0; i < images.length; i++) {
            images[i] = new Image(getClass().getResourceAsStream(
                    "/com/example/sae/image/ShiftyAnim/Shifty-" + (i + 1) + ".png"
            ));
        }

        shifty.niveauVitesseProperty().addListener((obs, oldValue, newValue) -> {
            mettreAJourImage(newValue.intValue());
        });

        mettreAJourImage(shifty.getNiveauVitesse());
    }

    private void mettreAJourImage(int niveauVitesse) {
        if (niveauVitesse < 0) {
            niveauVitesse = 0;
        }

        if (niveauVitesse > 13) {
            niveauVitesse = 13;
        }

        imageView.setImage(images[niveauVitesse]);
    }
}