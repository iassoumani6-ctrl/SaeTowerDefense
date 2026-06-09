package com.example.sae.modele;

import java.util.List;
import java.util.Random;

public class Ballon {

    private double x;
    private double y;

    private int pv;
    private int degats;
    private double pixDeplacement;

    private int indicePoint;
    private List<int[]> chemin;

    public Ballon(int iPv, int iDegats, double iVitesse) {
        Terrain terrain = new Terrain();
        Random random = new Random();

        this.pv = iPv;
        this.degats = iDegats;
        this.pixDeplacement = iVitesse;

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

            this.x = convertirColonneEnPixel(premiereCase[1]);
            this.y = convertirLigneEnPixel(premiereCase[0]);
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

    private double convertirColonneEnPixel(int colonne) {
        return colonne * Terrain.TAILLE_CASE + Terrain.TAILLE_CASE / 2.0;
    }

    private double convertirLigneEnPixel(int ligne) {
        return ligne * Terrain.TAILLE_CASE + Terrain.TAILLE_CASE / 2.0;
    }

    public void subirDegats(int degatsRecus) {
        this.pv -= degatsRecus;

        if (this.pv < 0) {
            this.pv = 0;
        }
    }

    public void tuerInstantanement() {
        this.pv = 0;
    }

    public boolean estMort() {
        return this.pv <= 0;
    }

    public String getCheminImage() {
        return "/com/example/sae/image/Anim_Ballon/Animation_ballon_vert.gif";
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getPv() {
        return this.pv;
    }

    public int getDegats() {
        return this.degats;
    }

    public double getPixDeplacement() {
        return this.pixDeplacement;
    }
}