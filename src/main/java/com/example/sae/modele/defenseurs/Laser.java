package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Tour;

public class Laser extends Tour {

    public Laser(int colonne, int ligne) {
        super(colonne, ligne, 160.0, 8, 300);
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Laser.png";
    }
}