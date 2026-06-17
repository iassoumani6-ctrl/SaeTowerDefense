package com.example.sae.modele.projectile;

/**
 * Projectile tornade : se déplace de la source vers la cible sur DUREE_MS.
 * Structure identique à Rayon_Laser.
 */
public class Rayon_Tornade {

    private static final long DUREE_MS = 500;

    private final double sourceX, sourceY;
    private final double cibleX,  cibleY;
    private final long   debutMs;
    private boolean terminee;

    public Rayon_Tornade(double sourceX, double sourceY,
                         double cibleX,  double cibleY) {
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.cibleX  = cibleX;
        this.cibleY  = cibleY;
        this.debutMs = System.currentTimeMillis();
        this.terminee = false;
    }

    public void update() {
        if (System.currentTimeMillis() >= debutMs + DUREE_MS) {
            terminee = true;
        }
    }

    /** Progression 0.0 (source) → 1.0 (cible). */
    public double getProgression() {
        if (terminee) return 1.0;
        return (double)(System.currentTimeMillis() - debutMs) / DUREE_MS;
    }

    /** Position X interpolée du projectile à l'instant t. */
    public double getXActuel() {
        return sourceX + getProgression() * (cibleX - sourceX);
    }

    /** Position Y interpolée du projectile à l'instant t. */
    public double getYActuel() {
        return sourceY + getProgression() * (cibleY - sourceY);
    }

    public boolean isTerminee() { return terminee; }
    public double  getSourceX() { return sourceX;  }
    public double  getSourceY() { return sourceY;  }
    public double  getCibleX()  { return cibleX;   }
    public double  getCibleY()  { return cibleY;   }
}
