package com.example.sae.modele.ennemis;

import com.example.sae.modele.Attaquant;
import com.example.sae.modele.Terrain;

public class EnnemieVert extends Attaquant {

    private int pv;
    private int degats;
    private int vitesse;

    public EnnemieVert() {

        this.pv = 50;
        this.degats = 5;
        this.vitesse = 3;
    }
    public int getDegats() {
        return this.degats;
    }

    public int getPv() {
        return this.pv;
    }
}
