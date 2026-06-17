package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;

/**
 * Le Shifty : mono-cible, portée et dégâts moyens. Particularité : plus il
 * attaque longtemps le MÊME ennemi, plus sa cadence augmente (jusqu'à un
 * plafond). Si la cible change / meurt / sort de portée, la cadence retombe.
 */
public class Shifty extends Tour {

    private IntegerProperty niveauVitesseProperty;

    private long derniereAttaqueShiftyMs;
    private int ticksSansCible;

    private static final int NIVEAU_MAX = 13;

    private static final long DELAI_MAX_MS = 500;
    private static final long DELAI_MIN_MS = 120;

    private static final int TICKS_POUR_REGRESSER = 15;
    private int paliers = 0;
    private static final long REDUCTION_PAR_PALIER_MS = 50;
    /** Nombre de paliers d'accélération maximum. */
    private static final int PALIERS_MAX = 8;


    public Shifty(int colonne, int ligne) {
        super(colonne, ligne, 125.0, 4, 500);

        this.niveauVitesseProperty = new SimpleIntegerProperty(0);
        this.derniereAttaqueShiftyMs = 0;
        this.ticksSansCible = 0;
    }


    @Override
    public boolean attaquer(List<Ballon> ennemis) {
        long maintenant = System.currentTimeMillis();

        Ballon cibleActuelle = choisirCible(ennemis);
        if (cibleActuelle == null) {
            return false;
        }

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
        if (getNiveauVitesse() < NIVEAU_MAX) {
            setNiveauVitesse(getNiveauVitesse() + 1);
        }

        if (paliers < PALIERS_MAX) {
            paliers++; // on accélère progressivement
        }
        return true;
    }

    public void mettreAJourVitesse(boolean ennemiDansPortee) {
        if (ennemiDansPortee) {
            ticksSansCible = 0;
            return;
        }

        ticksSansCible++;

        if (ticksSansCible >= TICKS_POUR_REGRESSER) {
            if (getNiveauVitesse() > 0) {
                setNiveauVitesse(getNiveauVitesse() - 1);
            }

            ticksSansCible = 0;
        }


    }

    private long calculerDelaiActuel() {
        long difference = DELAI_MAX_MS - DELAI_MIN_MS;
        long reduction = (difference * getNiveauVitesse()) / NIVEAU_MAX;

        return DELAI_MAX_MS - reduction;
    }

    public int getNiveauVitesse() {
        return niveauVitesseProperty.get();
    }

    public void setNiveauVitesse(int niveauVitesse) {
        this.niveauVitesseProperty.set(niveauVitesse);
    }

    public IntegerProperty niveauVitesseProperty() {
        return niveauVitesseProperty;
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/ShiftyAnim/Shifty-1.png";
    }
}
