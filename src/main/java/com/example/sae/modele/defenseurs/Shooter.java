package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Tour;

public class Shooter extends Tour {

    public Shooter(int colonne, int ligne) {
        super(colonne, ligne, 110.0, 10, 700);
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/ShooterAnim/Shooter.png";
    }
}