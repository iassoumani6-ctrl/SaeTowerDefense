package com.example.sae.modele;

import java.util.List;

public class Tour {

    public static final int TAILLE_CASES = 2;

    private int colonne;
    private int ligne;

    private double portee;
    private int degatsParTir;
    private long delaiAttaqueMs;
    private long derniereAttaqueMs;
    private int cout;
    private int vagueDeblocage;

    public Tour(int colonne, int ligne, double portee, int degatsParTir, long delaiAttaqueMs, int cout, int vagueDeblocage) {
        this.colonne = colonne;
        this.ligne = ligne;
        this.portee = portee;
        this.degatsParTir = degatsParTir;
        this.delaiAttaqueMs = delaiAttaqueMs;
        this.derniereAttaqueMs = 0;
        this.cout = cout;
        this.vagueDeblocage = vagueDeblocage;
    }

    public boolean tirerSur(Ballon ennemi) {
        long maintenant = System.currentTimeMillis();
        if (maintenant - derniereAttaqueMs < delaiAttaqueMs) return false;

        if (calculerDistance(ennemi) <= portee) {
            appliquerEffet(ennemi);
            derniereAttaqueMs = maintenant;
            return true;
        }
        return false;
    }

    protected void appliquerEffet(Ballon ennemi) {
        ennemi.subirDegats(degatsParTir);
    }

    public double calculerDistance(Ballon ennemi) {
        double cx = getCentrePixelX();
        double cy = getCentrePixelY();
        return Math.sqrt(Math.pow(ennemi.getX() - cx, 2) + Math.pow(ennemi.getY() - cy, 2));
    }

    public void tirerSurTous(List<Ballon> ennemis) {
        long maintenant = System.currentTimeMillis();
        if (maintenant - derniereAttaqueMs < delaiAttaqueMs) return;
        boolean aAtteint = false;
        for (Ballon b : ennemis) {
            if (!b.estMort() && calculerDistance(b) <= portee) {
                appliquerEffet(b);
                aAtteint = true;
            }
        }
        if (aAtteint) derniereAttaqueMs = maintenant;
    }

    public String getCheminImage() {
        return "/com/example/sae/image/tour.png";
    }

    public double getPixelX() { return colonne * Terrain.TAILLE_CASE; }
    public double getPixelY() { return ligne * Terrain.TAILLE_CASE; }

    public double getCentrePixelX() {
        return getPixelX() + (TAILLE_CASES * Terrain.TAILLE_CASE) / 2.0;
    }

    public double getCentrePixelY() {
        return getPixelY() + (TAILLE_CASES * Terrain.TAILLE_CASE) / 2.0;
    }

    public int getColonne() { return colonne; }
    public int getLigne() { return ligne; }
    public int getTailleCases() { return TAILLE_CASES; }
    public double getPortee() { return portee; }
    public int getDegatsParTir() { return degatsParTir; }
    public long getDelaiAttaqueMs() { return delaiAttaqueMs; }
    protected void setDelaiAttaqueMs(long delai) { this.delaiAttaqueMs = delai; }
    public int getCout() { return cout; }
    public int getVagueDeblocage() { return vagueDeblocage; }
}