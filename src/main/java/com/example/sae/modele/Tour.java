package com.example.sae.modele;

public class Tour {

    public static final int TAILLE_CASES = 2; // 2 cases x 2 cases = 64x64

    private int colonne;
    private int ligne;

    private double portee;
    private int degatsParTir;
    private long delaiAttaqueMs;
    private long derniereAttaqueMs;

    public Tour(int colonne, int ligne, double portee, int degatsParTir, long delaiAttaqueMs) {
        this.colonne = colonne;
        this.ligne = ligne;

        this.portee = portee;
        this.degatsParTir = degatsParTir;
        this.delaiAttaqueMs = delaiAttaqueMs;
        this.derniereAttaqueMs = 0;
    }

    public boolean tirerSur(Ballon ennemi) {
        long maintenant = System.currentTimeMillis();

        if (maintenant - derniereAttaqueMs < delaiAttaqueMs) {
            return false;
        }

        double cx = getCentrePixelX();
        double cy = getCentrePixelY();

        double ex = ennemi.getX();
        double ey = ennemi.getY();

        double distance = Math.sqrt(Math.pow(ex - cx, 2) + Math.pow(ey - cy, 2));

        if (distance <= portee) {
            ennemi.tuerInstantanement(); // provisoire sprint 2
            derniereAttaqueMs = maintenant;
            return true;
        }

        return false;
    }

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
}