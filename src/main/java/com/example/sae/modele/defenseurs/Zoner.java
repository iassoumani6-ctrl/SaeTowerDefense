package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;

import java.util.List;

/**
 * Le Zoner : attaque TOUS les ballons présents dans son rayon d'action
 * (sans exception) à chaque attaque. Sa portée est faible mais il touche
 * plusieurs ennemis en même temps.
 */
public class Zoner extends Tour {

    public Zoner(int colonne, int ligne) {
        super(colonne, ligne, 90.0, 18, 1000);
        //          portée  dégâts  délai(ms)
        super(colonne, ligne, 90.0, 10, 700);
    }

    @Override
    public boolean attaquer(List<Ballon> ennemis) {
        long maintenant = System.currentTimeMillis();

        if (!pretAAttaquer(maintenant)) {
            return false;
        }

        boolean aTouche = false;

        // On frappe tous les ballons vivants à portée, pas seulement un.
        for (Ballon ballon : ennemis) {
            if (!ballon.estMort() && estEnPortee(ballon)) {
                ballon.subirDegats(getDegatsParTir());
                aTouche = true;
            }
        }

        if (aTouche) {
            marquerAttaque(maintenant);
        }
        return aTouche;
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/ZonerAnim/ZonerRepos1.png";
    }
}