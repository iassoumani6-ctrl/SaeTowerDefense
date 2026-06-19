package com.example.sae.modele.projectile;

import com.example.sae.modele.Ballon;

import java.util.List;

public class BalleEnFeu {

    private double x;
    private double y;

    private final double cibleX;
    private final double cibleY;

    private final double vitesse = 5.0;

    private final Ballon cible;
    private final List<Ballon> ennemis;

    private boolean arrivee;

    public BalleEnFeu(double sourceX, double sourceY, Ballon cible, List<Ballon> ennemis) {
        this.x = sourceX;
        this.y = sourceY;

        this.cibleX = cible.getX();
        this.cibleY = cible.getY();

        this.cible = cible;
        this.ennemis = ennemis;

        this.arrivee = false;
    }

    public void update() {
        if (arrivee) {
            return;
        }

        double dx = cibleX - x;
        double dy = cibleY - y;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance <= vitesse) {
            x = cibleX;
            y = cibleY;
            arrivee = true;
        } else {
            x += (dx / distance) * vitesse;
            y += (dy / distance) * vitesse;
        }
    }

    public boolean isArrivee() {
        return arrivee;
    }

    public Ballon getCible() {
        return cible;
    }

    public List<Ballon> getEnnemis() {
        return ennemis;
    }

    public double getX() { return x; }
    public double getY() { return y; }
}