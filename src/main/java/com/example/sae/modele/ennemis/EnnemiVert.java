package com.example.sae.modele.ennemis;
import com.example.sae.modele.Attaquant;
import com.example.sae.modele.Terrain;

public class EnnemiVert extends Attaquant {
    private int ligne;
    private int colonne;
    private Terrain terrain;
    private int pv;
    private int degats;


    public EnnemiVert() {
        this.pv = 50;
        this.degats = 5;
    }

    public int getPv() { return this.pv; }

    public int getDegats() { return this.degats; }


}
