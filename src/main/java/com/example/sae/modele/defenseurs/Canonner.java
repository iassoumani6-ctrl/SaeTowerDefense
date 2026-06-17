package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;

import java.util.List;

/**
 * Le Canonnier : mortier de longue portée. Il vise un ballon, mais l'impact
 * de son projectile inflige des dégâts de zone à tous les ballons proches du
 * point d'impact. Peu de dégâts unitaires, mais redoutable contre les groupes.
 */
public class Canonner extends Tour {

    /** Rayon de l'explosion autour du point d'impact (en pixels). */
    private static final double RAYON_EXPLOSION = 70.0;

    public Canonner(int colonne, int ligne) {
        //          portée  dégâts  délai(ms)
        super(colonne, ligne, 240.0, 12, 1500);
    }

    @Override
    public boolean attaquer(List<Ballon> ennemis) {
        long maintenant = System.currentTimeMillis();

        if (!pretAAttaquer(maintenant)) {
            return false;
        }

        // Le projectile vise le ballon le plus avancé à portée.
        Ballon cible = choisirCible(ennemis);
        if (cible == null) {
            return false;
        }

        // Point d'impact = position de la cible visée.
        double impactX = cible.getX();
        double impactY = cible.getY();

        // Tous les ballons dans le rayon d'explosion encaissent les dégâts.
        for (Ballon ballon : ennemis) {
            if (ballon.estMort()) {
                continue;
            }
            double dx = ballon.getX() - impactX;
            double dy = ballon.getY() - impactY;
            if (Math.sqrt(dx * dx + dy * dy) <= RAYON_EXPLOSION) {
                ballon.subirDegats(getDegatsParTir());
            }
        }

        marquerAttaque(maintenant);
        return true;
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Canonner.png";
    }
}
