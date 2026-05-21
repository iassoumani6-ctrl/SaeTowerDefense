package com.example.sae.modele.ennemis;

import com.example.sae.modele.Attaquant;
import com.example.sae.modele.Terrain;

public class EnnemieVert extends Attaquant {

    private int pv;
    private int degats;
    private int vitesse;

    public EnnemieVert(int ligneDep, int colonneDep, Terrain terrain) {
        super(ligneDep, colonneDep, terrain);
        this.pv = 50;
        this.degats = 5;
        this.vitesse = 3;
    }
    public int getDegats() {
        return degats;
    }

    public int getPv() {
        return pv;
    }
}
