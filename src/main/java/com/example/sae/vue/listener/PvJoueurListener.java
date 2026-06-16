package com.example.sae.vue.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PvJoueurListener implements ChangeListener<Number> {

    private int pvMax;
    private ImageView coeursVieImageView;
    private Image[] imagesCoeur;

    public PvJoueurListener(int iPvMax, ImageView iCoeursVieImageView) {
        this.pvMax = iPvMax;
        this.coeursVieImageView = iCoeursVieImageView;

        this.imagesCoeur = new Image[7];
        imagesCoeur[0] = new Image(getClass().getResourceAsStream("/com/example/sae/image/Coeur/Coeur0.gif"));
        imagesCoeur[1] = new Image(getClass().getResourceAsStream("/com/example/sae/image/Coeur/Coeur1.gif"));
        imagesCoeur[2] = new Image(getClass().getResourceAsStream("/com/example/sae/image/Coeur/Coeur2.gif"));
        imagesCoeur[3] = new Image(getClass().getResourceAsStream("/com/example/sae/image/Coeur/Coeur3.gif"));
        imagesCoeur[4] = new Image(getClass().getResourceAsStream("/com/example/sae/image/Coeur/Coeur4.gif"));
        imagesCoeur[5] = new Image(getClass().getResourceAsStream("/com/example/sae/image/Coeur/Coeur5.gif"));
        imagesCoeur[6] = new Image(getClass().getResourceAsStream("/com/example/sae/image/Coeur/Coeur6.gif"));

        mettreAJourImage(iPvMax);
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        mettreAJourImage(newValue.intValue());
    }

    private void mettreAJourImage(int pvActuels) {
        if (pvActuels < 0) {
            pvActuels = 0;
        }

        double proportion = (double) pvActuels / pvMax;

        int indiceImage = (int) Math.round(proportion * 6);

        if (indiceImage < 0) {
            indiceImage = 0;
        }

        if (indiceImage > 6) {
            indiceImage = 6;
        }

        coeursVieImageView.setImage(imagesCoeur[indiceImage]);
    }
}