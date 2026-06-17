package com.example.sae.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Partie {

    private IntegerProperty pvJoueurProperty;
    private IntegerProperty argentJoueurProperty;

    public Partie(int iPvDepart, int iArgentDepart) {
        this.pvJoueurProperty = new SimpleIntegerProperty(iPvDepart);
        this.argentJoueurProperty = new SimpleIntegerProperty(iArgentDepart);
    }

    public void gagnerArgent(int iMontant) {
        setArgentJoueur(getArgentJoueur() + iMontant);
    }

    /** Vrai si le joueur a au moins {@code montant} pièces. */
    public boolean peutPayer(int montant) {
        return getArgentJoueur() >= montant;
    }

    /** Retire {@code montant} pièces (sans descendre sous 0). */
    public void depenserArgent(int montant) {
        int restant = getArgentJoueur() - montant;
        setArgentJoueur(Math.max(restant, 0));
    }

    public void perdrePv(int iDegats) {
        setPvJoueur(getPvJoueur() - iDegats);

        if (getPvJoueur() < 0) {
            setPvJoueur(0);
        }
    }

    public boolean partiePerdue() {
        return getPvJoueur() <= 0;
    }

    public int getArgentJoueur() {return this.argentJoueurProperty.get();}

    public void setArgentJoueur(int argentJoueur) {this.argentJoueurProperty.set(argentJoueur);}

    public IntegerProperty argentJoueurProperty() {return this.argentJoueurProperty;}

    public int getPvJoueur() {return this.pvJoueurProperty.get();}

    public void setPvJoueur(int pvJoueur) {this.pvJoueurProperty.set(pvJoueur);}

    public IntegerProperty pvJoueurProperty() {return this.pvJoueurProperty;}
}