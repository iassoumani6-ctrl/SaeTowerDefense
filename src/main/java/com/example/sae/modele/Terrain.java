package com.example.sae.modele;

public class Terrain {

    private int[][] codeTuiles = {
            {2,2,2,2,2,2,2,2,2,2,2,2},
            {1,1,1,1,2,2,1,1,1,1,1,2},
            {2,2,2,1,2,2,1,2,2,2,1,2},
            {2,1,1,1,2,2,1,2,2,2,1,2},
            {2,1,2,2,2,2,1,2,1,1,1,2},
            {2,1,1,1,1,1,1,2,1,2,2,2},
            {2,2,2,2,2,2,2,2,1,2,2,2},
            {2,2,2,2,2,2,2,2,1,2,2,2},
            {2,2,2,2,2,2,2,2,1,2,2,2},
            {2,2,2,2,2,2,2,2,1,2,2,2}};

    public int hauteur()  { return this.codeTuiles.length; }
    public int largeur()  { return this.codeTuiles[0].length; }
    public int codeTuile(int ligne, int colonne) { return this.codeTuiles[ligne][colonne]; }

    public boolean estChemin(int ligne, int colonne) {
        return ligne >= 0 && ligne < hauteur()
                && colonne >= 0 && colonne < largeur()
                && codeTuiles[ligne][colonne] == 1;
    }
}
