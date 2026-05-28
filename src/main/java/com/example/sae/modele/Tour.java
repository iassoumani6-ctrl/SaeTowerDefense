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
        this.portee       = 100.0;  // ~3 tuiles
        this.degatsParTir = 10;
        this.delaiTirMs   = 800;    // une attaque toutes les 800ms
        this.dernierTirMs = 0;
    }
    
    public boolean tirerSur(Attaquant ennemi) {
        long maintenant = System.currentTimeMillis();
        if (maintenant - dernierTirMs < delaiTirMs) return false;

        double cx = getCentrePixelX();
        double cy = getCentrePixelY();
        double ex = ennemi.getX() + 16;
        double ey = ennemi.getY() + 16;

        double distance = Math.sqrt(Math.pow(ex - cx, 2) + Math.pow(ey - cy, 2));
        if (distance <= portee) {
            ennemi.subirDegats(degatsParTir);
            dernierTirMs = maintenant;
            return true;
        }
        return false;
    }

    public double getCentrePixelX() {
        return colonne * Terrain.TAILLE_CASE + Terrain.TAILLE_CASE / 2.0;
    }
    public double getCentrePixelY() {
        return ligne * Terrain.TAILLE_CASE + Terrain.TAILLE_CASE / 2.0;
    }
    public double getPixelX() { return colonne * Terrain.TAILLE_CASE; }
    public double getPixelY() { return ligne   * Terrain.TAILLE_CASE; }
    public double getPortee() { return portee; }
    public int getColonne()   { return colonne; }
    public int getLigne()     { return ligne; }
}
