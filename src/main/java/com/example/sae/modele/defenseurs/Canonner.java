package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Tour;

public class Canonner extends Tour {

    public Canonner(int colonne, int ligne) {
        super(colonne, ligne, 100.0, 30, 1400);
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/CanonnerAnim/Canonner.png";
    }
}