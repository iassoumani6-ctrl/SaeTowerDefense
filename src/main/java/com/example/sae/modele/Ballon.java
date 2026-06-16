package com.example.sae.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;
import java.util.Random;

public class Ballon {

    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();
    private final IntegerProperty pv = new SimpleIntegerProperty();

    private int pvMax;
    private int degats;
    private double vitesseBase;
    private double pixDeplacement;
    private int recompense;

    private long finRalentissementMs = 0;
    private static final double FACTEUR_RALENTISSEMENT = 0.5;

    private int indicePoint;
    private List<int[]> chemin;

    public Ballon(int iPv, int iDegats, double iVitesse, int iRecompense) {
        Terrain terrain = new Terrain();
        Random random = new Random();

        this.pvMax = iPv;
        this.pv.set(iPv);
        this.degats = iDegats;
        this.vitesseBase = iVitesse;
        this.pixDeplacement = iVitesse;
        this.indicePoint = 0;
        this.recompense = iRecompense;

        int cheminAleatoire = random.nextInt(6) + 1;
        switch (cheminAleatoire) {
            case 1: this.chemin = terrain.trouverChemin(5, 0, 0, 19); break;
            case 2: this.chemin = terrain.trouverChemin(5, 0, 12, 34); break;
            case 3: this.chemin = terrain.trouverChemin(5, 0, 20, 16); break;
            case 4: this.chemin = terrain.trouverChemin(13, 0, 0, 19); break;
            case 5: this.chemin = terrain.trouverChemin(13, 0, 12, 34); break;
            default: this.chemin = terrain.trouverChemin(13, 0, 20, 16); break;
        }

        if (!chemin.isEmpty()) {
            int[] premiereCase = chemin.get(0);
            this.x.set(convertirColonneEnPixel(premiereCase[1]));
            this.y.set(convertirLigneEnPixel(premiereCase[0]));
        }
    }

    public void avancer() {
        if (chemin.isEmpty()) return;
        if (indicePoint >= chemin.size() - 1) return;

        if (System.currentTimeMillis() > finRalentissementMs) {
            this.pixDeplacement = vitesseBase;
        }

        int[] caseCible = chemin.get(indicePoint + 1);
        double cibleX = convertirColonneEnPixel(caseCible[1]);
        double cibleY = convertirLigneEnPixel(caseCible[0]);

        double dx = cibleX - x.get();
        double dy = cibleY - y.get();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance <= pixDeplacement) {
            x.set(cibleX);
            y.set(cibleY);
            indicePoint++;
        } else {
            x.set(x.get() + pixDeplacement * dx / distance);
            y.set(y.get() + pixDeplacement * dy / distance);
        }
    }

    public void ralentir(long dureeMs) {
        this.pixDeplacement = vitesseBase * FACTEUR_RALENTISSEMENT;
        this.finRalentissementMs = System.currentTimeMillis() + dureeMs;
    }

    public boolean estRalenti() {
        return System.currentTimeMillis() <= finRalentissementMs;
    }

    private double convertirColonneEnPixel(int colonne) {
        return colonne * Terrain.TAILLE_CASE + Terrain.TAILLE_CASE / 2.0;
    }

    private double convertirLigneEnPixel(int ligne) {
        return ligne * Terrain.TAILLE_CASE + Terrain.TAILLE_CASE / 2.0;
    }

    public void subirDegats(int degatsRecus) {
        int val = this.pv.get() - degatsRecus;
        this.pv.set(Math.max(0, val));
    }

    public void tuerInstantanement() { this.pv.set(0); }
    public boolean estMort() { return this.pv.get() <= 0; }

    // Propriétés JavaFX
    public DoubleProperty xProperty() { return x; }
    public DoubleProperty yProperty() { return y; }
    public IntegerProperty pvProperty() { return pv; }

    // Getters
    public double getX() { return x.get(); }
    public double getY() { return y.get(); }
    public int getPv() { return pv.get(); }
    public int getPvMax() { return pvMax; }
    public int getDegats() { return degats; }
    public double getPixDeplacement() { return pixDeplacement; }

    public String getCheminImage() {
        return "/com/example/sae/image/Anim_Ballon/Animation_ballon_vert.gif";
    }
}