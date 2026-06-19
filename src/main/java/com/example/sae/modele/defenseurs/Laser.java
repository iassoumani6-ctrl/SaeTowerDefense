package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;
import com.example.sae.modele.projectile.RayonLaser;

import java.util.List;

public class Laser extends Tour {

    private Ballon cibleVerrouillee;
    private RayonLaser attaqueActive;

    public Laser(int colonne, int ligne) {
        super(colonne, ligne, 160.0, 6, 150);
    }

    @Override
    public boolean attaquer(List<Ballon> ennemis) {
        long maintenant = System.currentTimeMillis();

        if (cibleVerrouillee != null
                && (cibleVerrouillee.estMort()
                || !estEnPortee(cibleVerrouillee)
                || !ennemis.contains(cibleVerrouillee))) {
            cibleVerrouillee = null;
        }

        if (cibleVerrouillee == null) {
            cibleVerrouillee = choisirCible(ennemis);
        }

        if (cibleVerrouillee == null) {
            return false;
        }

        if (!pretAAttaquer(maintenant)) {
            return false;
        }

        attaqueActive = new RayonLaser(
                getCentrePixelX(),
                getCentrePixelY(),
                cibleVerrouillee.getX(),
                cibleVerrouillee.getY()
        );

        cibleVerrouillee.subirDegats(getDegatsParTir());
        marquerAttaque(maintenant);

        return true;
    }

    public void updateProjectiles() {
        if (attaqueActive != null) {
            attaqueActive.update();

            if (attaqueActive.isTerminee()) {
                attaqueActive = null;
            }
        }
    }

    public RayonLaser getAttaqueActive() {
        return attaqueActive;
    }

    public Ballon getCibleVerrouillee() {
        return cibleVerrouillee;
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/LaserAnim/Laser.png";
    }
}