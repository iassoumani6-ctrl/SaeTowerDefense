package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Tour;

public class Ralentisseur extends Tour {

    public Ralentisseur(int colonne, int ligne) {
        super(colonne, ligne, 120.0, 3, 900);
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Ralentisseur.png";
    }
}