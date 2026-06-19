package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;
import com.example.sae.modele.projectile.BalleEnFeu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Canonner extends Tour {

    private static final double RAYON_EXPLOSION = 70.0;

    private final List<BalleEnFeu> ballesEnVol = new ArrayList<>();

    public Canonner(int colonne, int ligne) {
        super(colonne, ligne, 240.0, 12, 1500);
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

        ballesEnVol.add(
                new BalleEnFeu(
                        getCentrePixelX(),
                        getCentrePixelY(),
                        cible,
                        ennemis
                )
        );

        marquerAttaque(maintenant);
        return true;
    }

    public void updateProjectiles() {
        Iterator<BalleEnFeu> it = ballesEnVol.iterator();

        while (it.hasNext()) {
            BalleEnFeu balle = it.next();
            balle.update();

            if (balle.isArrivee()) {
                Ballon cible = balle.getCible();

                if (cible != null) {
                    double impactX = cible.getX();
                    double impactY = cible.getY();

                    for (Ballon ballon : balle.getEnnemis()) {
                        if (ballon.estMort()) {
                            continue;
                        }

                        double dx = ballon.getX() - impactX;
                        double dy = ballon.getY() - impactY;

                        if (Math.sqrt(dx * dx + dy * dy) <= RAYON_EXPLOSION) {
                            ballon.subirDegats(getDegatsParTir());
                        }
                    }
                }

                it.remove();
            }
        }
    }

    public List<BalleEnFeu> getBallesEnVol() {
        return ballesEnVol;
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/CanonnerAnim/Canonner.gif";
    }
}