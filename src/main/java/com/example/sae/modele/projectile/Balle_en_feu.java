package com.example.sae.modele.projectile;

import com.example.sae.modele.Ballon;

public class Balle_en_feu {

    private double x, y;
    private final double cibleX, cibleY;
    private final double vitesse = 5.0;
    private final Ballon cible;
    private boolean arrivee;

    public Balle_en_feu(double sourceX, double sourceY, Ballon cible) {
        this.x       = sourceX;
        this.y       = sourceY;
        this.cibleX  = cible.getX();
        this.cibleY  = cible.getY();
        this.cible   = cible;
        this.arrivee = false;
    }

    public void update() {
        if (arrivee) return;

        double dx       = cibleX - x;
        double dy       = cibleY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance <= vitesse) {
            x       = cibleX;
            y       = cibleY;
            arrivee = true;
            // Tue le ballon à l'impact
            cible.tuerInstantanement();
        } else {
            x += (dx / distance) * vitesse;
            y += (dy / distance) * vitesse;
        }
    }

    public boolean isArrivee() { return arrivee; }
    public double  getX()      { return x;        }
    public double  getY()      { return y;         }
}

