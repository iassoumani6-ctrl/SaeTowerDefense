package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;
import com.example.sae.modele.projectile.RayonTornade;

import java.util.List;

public class Ralentisseur extends Tour {

    private static final double FACTEUR_RALENTISSEMENT = 0.5;
    private static final long DUREE_RALENTISSEMENT_MS = 1500;

    private RayonTornade attaqueActive;

    public Ralentisseur(int colonne, int ligne) {
        super(colonne, ligne, 130.0, 3, 800);
    }

    @Override
    public boolean attaquer(List<Ballon> ennemis) {
        long maintenant = System.currentTimeMillis();

        if (!pretAAttaquer(maintenant)) {
            return false;
        }

        Ballon cible = choisirCible(ennemis);

        if (cible == null) {
            return false;
        }

        attaqueActive = new RayonTornade(
                getCentrePixelX(),
                getCentrePixelY(),
                cible.getX(),
                cible.getY()
        );

        cible.subirDegats(getDegatsParTir());
        cible.appliquerRalentissement(FACTEUR_RALENTISSEMENT, DUREE_RALENTISSEMENT_MS);

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

    public RayonTornade getAttaqueActive() {
        return attaqueActive;
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/RalentisseurAnim/Ralentisseur.png";
    }
}