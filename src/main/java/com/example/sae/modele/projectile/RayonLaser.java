package com.example.sae.modele.projectile;

public class RayonLaser {

    private double departX;
    private double departY;
    private double arriveeX;
    private double arriveeY;

    private int dureeRestanteTicks;

    public RayonLaser(double departX, double departY, double arriveeX, double arriveeY) {
        this.departX = departX;
        this.departY = departY;
        this.arriveeX = arriveeX;
        this.arriveeY = arriveeY;

        this.dureeRestanteTicks = 8;
    }

    public void update() {
        dureeRestanteTicks--;
    }

    public boolean isTerminee() {
        return dureeRestanteTicks <= 0;
    }

    public double getDepartX() { return departX; }
    public double getDepartY() { return departY; }
    public double getArriveeX() { return arriveeX; }
    public double getArriveeY() { return arriveeY; }
}