package com.example.sae.modele.ennemis;

import com.example.sae.modele.Attaquant;
import com.example.sae.modele.Terrain;

public class EnnemieRouge extends Attaquant {

    private int pv;
    private int degats;
    private int vitesse;

    public EnnemieRouge(int ligneDep, int colonneDep, Terrain terrain) {
        super(ligneDep, colonneDep, terrain);
        this.pv = 90;
        this.degats = 30;
        this.vitesse = 4;
    }
    public int getDegats() {
        return degats;
    }

    public int getPv() {
        return pv;
    }
}
