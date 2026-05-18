package com.example.sae.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Attaquant {

    private int ligne;
    private int colonne;
    private final Terrain terrain;
    private final Random random = new Random();

    // directions : haut, bas, gauche, droite
    private final int[] dl = {-1, 1, 0, 0};
    private final int[] dc = {0, 0, -1, 1};

    public Attaquant(int ligneDep, int colonneDep, Terrain terrain) {
        this.ligne   = ligneDep;
        this.colonne = colonneDep;
        this.terrain = terrain;
    }

    public void avancer() {
        List<int[]> voisins = new ArrayList<>();
        for (int d = 0; d < 4; d++) {
            int nl = ligne + dl[d];
            int nc = colonne + dc[d];
            if (terrain.estChemin(nl, nc)) {
                voisins.add(new int[]{nl, nc});
            }
        }
        if (voisins.isEmpty()) return;

        int[] choix = voisins.get(random.nextInt(voisins.size()));
        ligne   = choix[0];
        colonne = choix[1];
    }

    public int getLigne()   { return ligne; }
    public int getColonne() { return colonne; }
}
