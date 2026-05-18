package com.example.sae.modele;

public class Terrain {

    private int[][] codeTuiles = {
            {12,12,12,12,12,12,12,12},
            {0,0,0,0,0,0,0,0},
            {0,2,2,2,4,0,0,0},
            {0,0,0,0,3,0,0,0},
            {0,0,0,0,3,0,0,0},
            {0,0,0,0,8,2,2,0},
            {1,1,1,1,1,1,1,1}
    };
    public int getHauteur()  { return this.codeTuiles.length; }
    public int getLargeur()  { return this.codeTuiles[0].length; }
    public int getCodeTuile(int ligne, int colonne) { return this.codeTuiles[ligne][colonne]; }

    public boolean estChemin(int ligne, int colonne) {
        return ligne >= 0 && ligne < getHauteur()
                && colonne >= 0 && colonne < getLargeur()
                && codeTuiles[ligne][colonne] == 1;
    }
}
