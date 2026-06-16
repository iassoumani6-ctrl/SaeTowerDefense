package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;

public class Laser extends Tour {

    private Ballon cibleVerrouillee = null;

    public Laser(int colonne, int ligne) {
        super(colonne, ligne, 160.0, 8, 300, 200, 2);
    }

    public boolean tirerLaser(Ballon ennemi) {
        if (cibleVerrouillee == null || cibleVerrouillee.estMort()) {
            if (calculerDistance(ennemi) <= getPortee()) {
                cibleVerrouillee = ennemi;
            } else {
                return false;
            }
        }
        if (cibleVerrouillee == ennemi) {
            if (ennemi.estMort() || calculerDistance(ennemi) > getPortee()) {
                cibleVerrouillee = null;
                return false;
            }
            return tirerSur(ennemi);
        }
        return false;
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Laser.png";
    }
}