package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;

import java.util.List;

/**
 * Le Shifty : mono-cible, portée et dégâts moyens. Particularité : plus il
 * attaque longtemps le MÊME ennemi, plus sa cadence augmente (jusqu'à un
 * plafond). Si la cible change / meurt / sort de portée, la cadence retombe.
 */
public class Shifty extends Tour {

    /** Délai minimal atteignable (cadence maximale). */
    private static final long DELAI_MIN_MS = 150;
    /** Réduction du délai gagnée à chaque tir consécutif. */
    private static final long REDUCTION_PAR_PALIER_MS = 50;
    /** Nombre de paliers d'accélération maximum. */
    private static final int PALIERS_MAX = 8;

    private Ballon cibleActuelle;
    private int paliers = 0;

    public Shifty(int colonne, int ligne) {
        //          portée  dégâts  délai(ms)
        super(colonne, ligne, 150.0, 12, 600);
    }

    @Override
    public boolean attaquer(List<Ballon> ennemis) {
        long maintenant = System.currentTimeMillis();

        // Cible encore valide ? Sinon on réinitialise la montée en cadence.
        if (cibleActuelle != null
                && (cibleActuelle.estMort()
                    || !estEnPortee(cibleActuelle)
                    || !ennemis.contains(cibleActuelle))) {
            cibleActuelle = null;
            paliers = 0;
        }

        // Acquisition d'une nouvelle cible : la cadence repart à zéro.
        if (cibleActuelle == null) {
            cibleActuelle = choisirCible(ennemis);
            paliers = 0;
        }

        if (cibleActuelle == null) {
            return false;
        }

        // Délai courant réduit selon le nombre de paliers accumulés.
        long delaiCourant = Math.max(
                DELAI_MIN_MS,
                getDelaiAttaqueMs() - (long) paliers * REDUCTION_PAR_PALIER_MS);

        if (maintenant - getDerniereAttaqueMs() < delaiCourant) {
            return false;
        }

        cibleActuelle.subirDegats(getDegatsParTir());
        marquerAttaque(maintenant);

        if (paliers < PALIERS_MAX) {
            paliers++; // on accélère progressivement
        }
        return true;
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Shifty.gif";
    }
}
