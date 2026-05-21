package com.example.sae.modele.ennemis;
import com.example.sae.modele.Attaquant;
import com.example.sae.modele.Terrain;
/*
*   Ennemi le plus faible
* */
public class EnnemiVert extends Attaquant {
    private int ligne;
    private int colonne;
    private Terrain terrain;
    private int pv;
    private int degats;
    private int vitesse;


    public EnnemiVert(int ligneDep, int colonneDep, Terrain terrain) {
        super(ligneDep, colonneDep, terrain);
        this.pv = 50;
        this.degats = 5;
        this.vitesse = 3;
    }

    public int getPv() { return this.pv; }

    public int getDegats() { return this.degats; }


}
