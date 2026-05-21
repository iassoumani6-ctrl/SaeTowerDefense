package com.example.sae.modele.ennemis;

import com.example.sae.modele.Attaquant;
import com.example.sae.modele.Terrain;

public class EnnemieOrange extends Attaquant {
    private int pv;
    private int degats;
    private int vitesse;

    public EnnemieOrange(int ligneDep, int colonneDep, Terrain terrain) {

        this.pv = 70;
        this.degats = 15;
        this.vitesse = 4;
    }

    public int getDegats() {
        return this.degats;
    }

    public int getPv() {
        return this.pv;
    }
}
