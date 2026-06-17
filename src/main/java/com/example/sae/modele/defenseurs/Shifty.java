package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Shifty extends Tour {

    private IntegerProperty niveauVitesseProperty;

    private long derniereAttaqueShiftyMs;
    private int ticksSansCible;

    private static final int NIVEAU_MAX = 13;

    private static final long DELAI_MAX_MS = 500;
    private static final long DELAI_MIN_MS = 120;

    private static final int TICKS_POUR_REGRESSER = 15;

    public Shifty(int colonne, int ligne) {
        super(colonne, ligne, 125.0, 4, 500);

        this.niveauVitesseProperty = new SimpleIntegerProperty(0);
        this.derniereAttaqueShiftyMs = 0;
        this.ticksSansCible = 0;
    }

    @Override
    public boolean tirerSur(Ballon ennemi) {
        if (!estDansPortee(ennemi)) {
            return false;
        }

        long maintenant = System.currentTimeMillis();
        long delaiActuel = calculerDelaiActuel();

        if (maintenant - derniereAttaqueShiftyMs < delaiActuel) {
            return false;
        }

        ennemi.subirDegats(getDegatsParTir());
        derniereAttaqueShiftyMs = maintenant;

        if (getNiveauVitesse() < NIVEAU_MAX) {
            setNiveauVitesse(getNiveauVitesse() + 1);
        }

        ticksSansCible = 0;

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