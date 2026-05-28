package com.example.sae.modele;

public class Attaquant {

    private double x;
    private double y;
    private int indicePoint;
    private double pixDeplacement;

    protected int pv;
    protected int degats;
    protected int vitesse;

    private final double[][] chemin = {
            {0, 96},
            {320, 96},
            {320, 224},
            {64, 224},
            {64, 352},
            {416, 352},
            {416, 96},
            {736, 96},
            {736, 352},
            {992, 352},
            {992, 512},
            {256, 512},
            {256, 704}
    };

    public Attaquant() {
        this.x = chemin[0][0];
        this.y = chemin[0][1];
        this.pixDeplacement = 1;
        this.indicePoint = 0;
        this.pv      = 50;
        this.degats  = 5;
        this.vitesse = 1;
    }

    public void avancer() {
        if (indicePoint >= chemin.length - 1) return;

        double cibleX = chemin[indicePoint + 1][0];
        double cibleY = chemin[indicePoint + 1][1];
        double dx = cibleX - x;
        double dy = cibleY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance <= pixDeplacement) {
            x = cibleX;
            y = cibleY;
            indicePoint++;
        } else {
            x += pixDeplacement * dx / distance;
            y += pixDeplacement * dy / distance;
        }
    }

    public void subirDegats(int degats) {
        this.pv -= degats;
    }

    public boolean estMort() {
        return this.pv <= 0;
    }

    public int getPv()      { return this.pv; }
    public int getDegats()  { return this.degats; }
    public int getVitesse() { return this.vitesse; }
    public double getX()    { return this.x; }
    public double getY()    { return this.y; }
}
