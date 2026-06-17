package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;
import com.example.sae.modele.projectile.Rayon_Tornade;

public class Ralentisseur extends Tour {

    private Rayon_Tornade attaqueActive;

    public Ralentisseur(int colonne, int ligne) {
        super(colonne, ligne, 120.0, 3, 900);
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
            attaqueActive = new Rayon_Tornade(cx, cy, ex, ey);
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

    public Rayon_Tornade getAttaqueActive() {
        return attaqueActive;
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Ralentisseur.png";
    }
}
