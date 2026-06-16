package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Tour;

public class Shooter extends Tour {

    public Shooter(int colonne, int ligne) {
        super(colonne, ligne, 220.0, 40, 1800, 150, 1);
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Shooter.png";
    }
}