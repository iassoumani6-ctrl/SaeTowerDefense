package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Tour;

public class Shifty extends Tour {

    public Shifty(int colonne, int ligne) {
        super(colonne, ligne, 90.0, 15, 500);
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Shifty.gif";
    }
}