package com.example.sae.modele.ennemis;

import com.example.sae.modele.Attaquant;
import com.example.sae.modele.Terrain;

public class EnnemieJaune extends Attaquant {

    private int pv;
    private int degats;
    private int vitesse;

    public EnnemieJaune(int ligneDep, int colonneDep, Terrain terrain) {
        super(ligneDep, colonneDep, terrain);
        this.pv = 60;
        this.degats = 10;
        this.vitesse = 3;
    }
    public int getDegats() {
        return degats;
    }

    public int getPv() {
        return pv;
    }
}
