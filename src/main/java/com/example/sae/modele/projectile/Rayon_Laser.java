package com.example.sae.modele.projectile;

public class Rayon_Laser {
    private static final long DUREE_MS = 300;

    private final double sourceX, sourceY;
    private final double cibleX, cibleY;
    private final long debutMs;
    private boolean terminee;

    public Rayon_Laser(double sourceX, double sourceY, double cibleX, double cibleY) {
        this.sourceX  = sourceX;
        this.sourceY  = sourceY;
        this.cibleX   = cibleX;
        this.cibleY   = cibleY;
        this.debutMs  = System.currentTimeMillis();
        this.terminee = false;
    }

    public void update() {
        if (System.currentTimeMillis() >= debutMs + DUREE_MS) {
            terminee = true;
        }
    }

    public double getProgression() {
        if (terminee) return 1.0;
        return (double)(System.currentTimeMillis() - debutMs) / DUREE_MS;
    }

    public boolean isTerminee()  { return terminee;  }
    public double  getSourceX()  { return sourceX;   }
    public double  getSourceY()  { return sourceY;   }
    public double  getCibleX()   { return cibleX;    }
    public double  getCibleY()   { return cibleY;    }
}

