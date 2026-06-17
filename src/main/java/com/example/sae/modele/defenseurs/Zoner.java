package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Tour;

public class Zoner extends Tour {

    public Zoner(int colonne, int ligne) {
        super(colonne, ligne, 90.0, 18, 1000);
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/ZonerAnim/ZonerRepos1.png";
    }
}