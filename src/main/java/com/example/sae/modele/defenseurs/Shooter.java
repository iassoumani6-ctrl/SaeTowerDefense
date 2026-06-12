package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;
import com.example.sae.modele.projectile.Rayon_Laser;

public class Shooter extends Tour {
    private Rayon_Laser attaqueActive;

    public Shooter(int colonne, int ligne) {
        super(colonne, ligne, 110.0, 10, 700);
    }
    @Override
    public boolean tirerSur(Ballon ennemi) {
        long maintenant = System.currentTimeMillis();

        if (maintenant - getDerniereAttaqueMs() < getDelaiAttaqueMs()) {
            return false;
        }

        double cx = getCentrePixelX();
        double cy = getCentrePixelY();
        double ex = ennemi.getX();
        double ey = ennemi.getY();

        double distance = Math.sqrt(Math.pow(ex - cx, 2) + Math.pow(ey - cy, 2));

        if (distance <= getPortee()) {
            attaqueActive = new Rayon_Laser(cx, cy, ex, ey);
            ennemi.tuerInstantanement();
            setDerniereAttaqueMs(maintenant);
            return true;
        }

        return false;
    }

    public void update() {
        if (attaqueActive != null) {
            attaqueActive.update();
            if (attaqueActive.isTerminee()) {
                attaqueActive = null;
            }
        }
    }

    public Rayon_Laser getAttaqueActive() {
        return attaqueActive;
    }
    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Shooter.gif";
    }
}