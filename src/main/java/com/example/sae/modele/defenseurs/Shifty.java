package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;

public class Shifty extends Tour {

    private Ballon cibleCourante = null;
    private static final long DELAI_BASE = 500;
    private static final long DELAI_MIN  = 100;

    public Shifty(int colonne, int ligne) {
        super(colonne, ligne, 90.0, 15, DELAI_BASE, 100, 1);
    }

    @Override
    public boolean tirerSur(Ballon ennemi) {
        if (cibleCourante != ennemi) {
            cibleCourante = ennemi;
            setDelaiAttaqueMs(DELAI_BASE); // reset à la nouvelle cible
        }
        boolean a = super.tirerSur(ennemi);
        if (a) {
            long nouveau = Math.max(DELAI_MIN, (long)(getDelaiAttaqueMs() * 0.90));
            setDelaiAttaqueMs(nouveau);
        }
        return a;
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Shifty.gif";
    }
}