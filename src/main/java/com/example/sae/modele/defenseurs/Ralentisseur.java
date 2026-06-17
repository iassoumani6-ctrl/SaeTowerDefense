package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;

import java.util.List;

/**
 * Le Ralentisseur : inflige peu de dégâts mais applique un ralentissement
 * temporaire au ballon touché, laissant plus de temps aux autres tours.
 */
public class Ralentisseur extends Tour {

    /** Vitesse réduite à 50 % pendant l'effet. */
    private static final double FACTEUR_RALENTISSEMENT = 0.5;
    /** Durée du ralentissement en millisecondes. */
    private static final long DUREE_RALENTISSEMENT_MS = 1500;

    public Ralentisseur(int colonne, int ligne) {
        //          portée  dégâts  délai(ms)
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

        cible.subirDegats(getDegatsParTir());
        cible.appliquerRalentissement(FACTEUR_RALENTISSEMENT, DUREE_RALENTISSEMENT_MS);
        marquerAttaque(maintenant);
        return true;
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/RalentisseurAnim/Ralentisseur.png";
    }
}
