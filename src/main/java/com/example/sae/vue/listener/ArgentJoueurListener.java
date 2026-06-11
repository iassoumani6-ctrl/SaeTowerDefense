package com.example.sae.vue.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ArgentJoueurListener implements ChangeListener<Number> {

    @Override
    public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {

        int argent = newVal.intValue();

        if (argent >= 150) {
            System.out.println("Assez d'argent pour acheter certaines tours");
        } else {
            System.out.println("Pas assez d'argent pour certaines tours");
        }
    }
}