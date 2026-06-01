package com.example.sae.modele;

public class Tour {

    private int colonne;
    private int ligne;

    private double portee;       // en pixels
    private int degatsParTir;
    private long delaiTirMs;
    private long dernierTirMs;

    public Tour(int colonne, int ligne) {
        this.colonne      = colonne;
        this.ligne        = ligne;
        this.portee       = 100.0;  //  environ3 tuiles
        this.degatsParTir = 10;
        this.delaiTirMs   = 800;    // une attaque toutes les 800ms
        this.dernierTirMs = 0;
    }

    public boolean tirerSur(Ballon ennemi) {
        long maintenant = System.currentTimeMillis();
        if (maintenant - dernierTirMs < delaiTirMs) return false;

        double cx = getCentrePixelX();
        double cy = getCentrePixelY();
        double ex = ennemi.getX();
        double ey = ennemi.getY();

        double distance = Math.sqrt(Math.pow(ex - cx, 2) + Math.pow(ey - cy, 2));
        if (distance <= portee) {
            ennemi.tuerInstantanement();
            dernierTirMs = maintenant;
            return true;
        }
        return false;
    }

    public double getCentrePixelX() {
        return this.colonne * Terrain.TAILLE_CASE + Terrain.TAILLE_CASE / 2.0;
    }
    public double getCentrePixelY() {
        return ligne * Terrain.TAILLE_CASE + Terrain.TAILLE_CASE / 2.0;
    }
    public double getPixelX() { return this.colonne * Terrain.TAILLE_CASE; }
    public double getPixelY() { return this.ligne   * Terrain.TAILLE_CASE; }
    public double getDégats() { return this.degatsParTir; }
    public double getPortee() { return this.portee; }
    public int getColonne()   { return this.colonne; }
    public int getLigne()     { return this.ligne; }
}
