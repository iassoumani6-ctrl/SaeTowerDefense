package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;
import java.util.List;

public class Canonner extends Tour {

    private static final double RAYON_EXPLOSION = 80.0;

    public Canonner(int colonne, int ligne) {
        super(colonne, ligne, 200.0, 20, 1400, 500, 5);
    }

    public boolean tirerCanon(Ballon cible, List<Ballon> tous) {
        if (!tirerSur(cible)) return false;
        double ix = cible.getX();
        double iy = cible.getY();
        for (Ballon b : tous) {
            if (b != cible && !b.estMort()) {
                double d = Math.sqrt(Math.pow(b.getX() - ix, 2) + Math.pow(b.getY() - iy, 2));
                if (d <= RAYON_EXPLOSION) {
                    b.subirDegats(getDegatsParTir());
                }
            }
        }
        return true;
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Canonner.png";
    }
}