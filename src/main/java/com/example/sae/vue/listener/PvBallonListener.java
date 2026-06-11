package com.example.sae.vue.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PvBallonListener implements ChangeListener<Number> {

    private Rectangle barreVie;
    private int pvMax;
    private double largeurMax;

    public PvBallonListener(Rectangle barreVie, int pvMax, double largeurMax) {
        this.barreVie = barreVie;
        this.pvMax = pvMax;
        this.largeurMax = largeurMax;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {

        double proportion = newVal.doubleValue() / pvMax;

        if (proportion < 0) {
            proportion = 0;
        }

        barreVie.setWidth(largeurMax * proportion);

        if (proportion > 0.5) {
            barreVie.setFill(Color.GREEN);
        } else if (proportion > 0.25) {
            barreVie.setFill(Color.ORANGE);
        } else {
            barreVie.setFill(Color.RED);
        }
    }
}