package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;

import java.util.List;

/**
 * Le Shooter (= le « Sniper » de l'énoncé) : tour de longue portée,
 * mono-cible. Inflige de très gros dégâts mais possède une cadence lente.
 *
 * Comportement = attaque mono-cible de base (héritée de Tour) ; il suffit
 * de régler les statistiques : grande portée, gros dégâts, long délai.
 */
public class Shooter extends Tour {

    public Shooter(int colonne, int ligne) {
        //          portée  dégâts  délai(ms)
        super(colonne, ligne, 260.0, 80, 1800);
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
        marquerAttaque(maintenant);
        return true;
    }


        @Override
    public String getCheminImage() {
        return "/com/example/sae/image/ShooterAnim/Shooter.gif";
    }
}
