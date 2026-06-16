package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Tour;

public class Zoner extends Tour {

    public Zoner(int colonne, int ligne) {
        super(colonne, ligne, 80.0, 5, 1000, 300, 3);
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Zoner.png";
    }
}