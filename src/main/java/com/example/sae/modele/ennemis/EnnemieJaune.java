package com.example.sae.modele.ennemis;

import com.example.sae.modele.Attaquant;
import com.example.sae.modele.Terrain;

public class EnnemieJaune extends Attaquant {

    private int pv;
    private int degats;
    private int vitesse;

    public EnnemieJaune() {

        this.pv = 60;
        this.degats = 10;
        this.vitesse = 3;
    }
    public int getDegats() {
        return this.degats;
    }
    public int getPv() {
        return this.pv;
    }
}
