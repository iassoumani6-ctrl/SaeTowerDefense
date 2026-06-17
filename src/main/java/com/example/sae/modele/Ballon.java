package com.example.sae.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;
import java.util.Random;

public class Ballon {

    private DoubleProperty xProperty;
    private DoubleProperty yProperty;

    /** Vitesse maximale autorisée pour un ennemi (cf. énoncé : vitesse max 4). */
    public static final double VITESSE_MAX = 4.0;

    private IntegerProperty pvProperty;
    private int pvMax;
    private int degats;
    private double pixDeplacement;
    private int recompense;

    // --- Effet de ralentissement (tour Ralentisseur) ---
    private double facteurRalentissement = 1.0;
    private long ralentissementFinMs = 0;

    private int indicePoint;
    private List<int[]> chemin;

    public Ballon(int iPv, int iDegats, double iVitesse, int iRecompense) {
        Terrain terrain = new Terrain();
        Random random = new Random();

        this.xProperty = new SimpleDoubleProperty();
        this.yProperty = new SimpleDoubleProperty();

        this.pvProperty = new SimpleIntegerProperty(iPv);
        this.pvMax = iPv;
        this.degats = iDegats;
        this.pixDeplacement = Math.min(iVitesse, VITESSE_MAX);
        this.recompense = iRecompense;

        this.indicePoint = 0;

        int cheminAleatoire = random.nextInt(6) + 1;

        switch (cheminAleatoire) {
            case 1:
                this.chemin = terrain.trouverChemin(5, 0, 0, 19);
                break;

            case 2:
                this.chemin = terrain.trouverChemin(5, 0, 12, 34);
                break;

            case 3:
                this.chemin = terrain.trouverChemin(5, 0, 20, 16);
                break;

            case 4:
                this.chemin = terrain.trouverChemin(13, 0, 0, 19);
                break;

            case 5:
                this.chemin = terrain.trouverChemin(13, 0, 12, 34);
                break;

            default:
                this.chemin = terrain.trouverChemin(13, 0, 20, 16);
                break;
        }

        if (!chemin.isEmpty()) {
            int[] premiereCase = chemin.get(0);

            setX(convertirColonneEnPixel(premiereCase[1]));
            setY(convertirLigneEnPixel(premiereCase[0]));
        }
    }

    public void avancer() {
        if (chemin.isEmpty()) {
            return;
        }

        if (indicePoint >= chemin.size() - 1) {
            return;
        }

        int[] caseCible = chemin.get(indicePoint + 1);

        double cibleX = convertirColonneEnPixel(caseCible[1]);
        double cibleY = convertirLigneEnPixel(caseCible[0]);

        double x = getX();
        double y = getY();

        double dx = cibleX - x;
        double dy = cibleY - y;

        double distance = Math.sqrt(dx * dx + dy * dy);

        double vitesse = vitesseActuelle();

        if (distance <= vitesse) {
            setX(cibleX);
            setY(cibleY);
            indicePoint++;
        } else {
            setX(x + vitesse * dx / distance);
            setY(y + vitesse * dy / distance);
        }
    }

    /**
     * Vitesse effective ce tick : vitesse de base, éventuellement réduite
     * par un ralentissement encore actif, et toujours bornée à VITESSE_MAX.
     */
    private double vitesseActuelle() {
        double vitesse = pixDeplacement;
        if (System.currentTimeMillis() < ralentissementFinMs) {
            vitesse = pixDeplacement * facteurRalentissement;
        }
        return Math.min(vitesse, VITESSE_MAX);
    }

    /**
     * Applique un ralentissement temporaire. Si un ralentissement est déjà
     * actif, on conserve le plus fort et on rafraîchit sa durée.
     *
     * @param facteur  multiplicateur de vitesse (ex : 0.5 = -50%)
     * @param dureeMs  durée de l'effet en millisecondes
     */
    public void appliquerRalentissement(double facteur, long dureeMs) {
        long maintenant = System.currentTimeMillis();
        boolean dejaRalenti = maintenant < ralentissementFinMs;

        if (!dejaRalenti || facteur < this.facteurRalentissement) {
            this.facteurRalentissement = facteur;
        }
        this.ralentissementFinMs = maintenant + dureeMs;
    }

    private double convertirColonneEnPixel(int colonne) {
        return colonne * Terrain.TAILLE_CASE + Terrain.TAILLE_CASE / 2.0;
    }

    private double convertirLigneEnPixel(int ligne) {
        return ligne * Terrain.TAILLE_CASE + Terrain.TAILLE_CASE / 2.0;
    }

    public void subirDegats(int degatsRecus) {
        setPv(getPv() - degatsRecus);

        if (getPv() < 0) {
            setPv(0);
        }
    }
    public boolean estArrivee() {
        return !chemin.isEmpty() && indicePoint >= chemin.size() - 1;
    }

    public int getRecompense() {return this.recompense;}

    public void tuerInstantanement() {setPv(0);}

    public boolean estMort() {return getPv() <= 0;}

    public int getPv() {return this.pvProperty.getValue();}

    public void setPv(int pv) {this.pvProperty.setValue(pv);}

    public IntegerProperty pvProperty() {return this.pvProperty;}

    public int getPvMax() {return this.pvMax;}

    public String getCheminImage() {return "/com/example/sae/image/Anim_Ballon/Animation_ballon_vert.gif";}

    public double getX() {return this.xProperty.getValue();}

    public void setX(double x) {this.xProperty.setValue(x);}

    public DoubleProperty xProperty() {return this.xProperty;}

    public DoubleProperty yProperty() {return this.yProperty;}

    public double getY() {return this.yProperty.getValue();}

    public void setY(double y) {this.yProperty.setValue(y);}

    public int getDegats() {
        return this.degats;
    }

    public double getPixDeplacement() {
        return vitesseActuelle();
    }

    public int getIndicePoint() {
        return this.indicePoint;
    }
}