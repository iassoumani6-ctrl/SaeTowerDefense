package com.example.sae.vue.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class PvJoueurListener implements ChangeListener<Number> {

    private int pvMax;

    public PvJoueurListener(int pvMax) {
        this.pvMax = pvMax;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {

        int pvActuels = newVal.intValue();

        if (pvActuels <= 0) {
            System.out.println("PERDU");
        } else if (pvActuels <= pvMax / 2) {
            System.out.println("Attention : PV sous 50%");
        }
    }
}