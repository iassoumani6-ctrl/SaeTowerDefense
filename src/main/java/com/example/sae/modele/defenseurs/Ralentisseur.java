package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;

public class Ralentisseur extends Tour {

    private static final long DUREE_RALENTISSEMENT_MS = 2000;

    public Ralentisseur(int colonne, int ligne) {
        super(colonne, ligne, 120.0, 3, 900, 350, 4);
    }

    @Override
    protected void appliquerEffet(Ballon ennemi) {
        ennemi.subirDegats(getDegatsParTir());
        ennemi.ralentir(DUREE_RALENTISSEMENT_MS);
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Ralentisseur.png";
    }
}