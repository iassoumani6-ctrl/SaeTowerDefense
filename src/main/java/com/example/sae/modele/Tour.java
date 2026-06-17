package com.example.sae.modele;

import java.util.List;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class Tour {

    public static final int TAILLE_CASES = 2; // 2 cases x 2 cases = 64x64

    private int colonne;
    private int ligne;

    private double portee;
    private int degatsParTir;
    private long delaiAttaqueMs;
    private long derniereAttaqueMs;
    private BooleanProperty enAttaqueProperty;

    public Tour(int colonne, int ligne, double portee, int degatsParTir, long delaiAttaqueMs) {
        this.colonne = colonne;
        this.ligne = ligne;

        this.portee = portee;
        this.degatsParTir = degatsParTir;
        this.delaiAttaqueMs = delaiAttaqueMs;
        this.derniereAttaqueMs = 0;

        this.enAttaqueProperty = new SimpleBooleanProperty(false);
    }

    // ============================================================
    //  COMBAT
    // ============================================================
    public abstract boolean attaquer(List<Ballon> ennemi);




//    /**
//     * Comportement d'attaque par défaut : mono-cible.
//     * Vise le ballon le plus avancé sur le chemin et à portée, puis lui
//     * inflige {@code degatsParTir} si le délai entre deux tirs est écoulé.
//     *
//     * @param ennemis tous les ballons actuellement présents
//     * @return true si la tour a tiré ce tick
//     */
//    public boolean attaquer(List<Ballon> ennemis) {
//        long maintenant = System.currentTimeMillis();
//
//        if (!pretAAttaquer(maintenant)) {
//            return false;
//        }
//
//        if (estDansPortee(ennemi)) {
//            ennemi.subirDegats(degatsParTir);
//            derniereAttaqueMs = maintenant;
//            return true;
//        }
//        cible.subirDegats(degatsParTir);
//        marquerAttaque(maintenant);
//        return true;
//    }
//        return false;
//    }

    public boolean estDansPortee(Ballon ennemi) {
        double cx = getCentrePixelX();
        double cy = getCentrePixelY();

        double ex = ennemi.getX();
        double ey = ennemi.getY();

        double distance = Math.sqrt(Math.pow(ex - cx, 2) + Math.pow(ey - cy, 2));

        return distance <= portee;
    }

    public boolean isEnAttaque() {
        return enAttaqueProperty.get();
    }

    public void setEnAttaque(boolean enAttaque) {
        this.enAttaqueProperty.set(enAttaque);
    }

    public BooleanProperty enAttaqueProperty() {
        return enAttaqueProperty;
    }



    /** Vrai si le délai entre deux attaques est écoulé. */
    protected boolean pretAAttaquer(long maintenant) {
        return maintenant - derniereAttaqueMs >= delaiAttaqueMs;
    }

    /** Enregistre l'instant de la dernière attaque (réarme le cooldown). */
    protected void marquerAttaque(long maintenant) {
        this.derniereAttaqueMs = maintenant;
    }

    /** Vrai si le ballon est dans le rayon d'action de la tour. */
    protected boolean estEnPortee(Ballon ballon) {
        double dx = ballon.getX() - getCentrePixelX();
        double dy = ballon.getY() - getCentrePixelY();
        return Math.sqrt(dx * dx + dy * dy) <= portee;
    }

    /**
     * Sélectionne la cible prioritaire : le ballon vivant, à portée,
     * et le plus avancé sur le chemin (le plus menaçant).
     */
    protected Ballon choisirCible(List<Ballon> ennemis) {
        Ballon meilleure = null;
        int meilleurIndice = -1;

        for (Ballon ballon : ennemis) {
            if (ballon.estMort() || !estEnPortee(ballon)) {
                continue;
            }
            if (ballon.getIndicePoint() > meilleurIndice) {
                meilleurIndice = ballon.getIndicePoint();
                meilleure = ballon;
            }
        }
        return meilleure;
    }

    // ============================================================
    //  AFFICHAGE / POSITION
    // ============================================================

    public String getCheminImage() {
        return "/com/example/sae/image/tour.png";
    }

    public double getPixelX() {
        return colonne * Terrain.TAILLE_CASE;
    }

    public double getPixelY() {
        return ligne * Terrain.TAILLE_CASE;
    }

    public double getCentrePixelX() {
        return getPixelX() + (TAILLE_CASES * Terrain.TAILLE_CASE) / 2.0;
    }

    public double getCentrePixelY() {
        return getPixelY() + (TAILLE_CASES * Terrain.TAILLE_CASE) / 2.0;
    }

    public int getColonne() {
        return colonne;
    }

    public int getLigne() {
        return ligne;
    }

    public int getTailleCases() {
        return TAILLE_CASES;
    }

    public double getPortee() {
        return portee;
    }

    public int getDegatsParTir() {
        return degatsParTir;
    }

    public long getDelaiAttaqueMs() {
        return delaiAttaqueMs;
    }

    protected long getDerniereAttaqueMs() {
        return derniereAttaqueMs;
    }
}
