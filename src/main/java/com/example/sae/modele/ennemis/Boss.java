package com.example.sae.modele.ennemis;

import com.example.sae.modele.Attaquant;
import com.example.sae.modele.Terrain;
import javafx.scene.image.Image;

public class Boss extends Attaquant {

    private int pv;
    private int degats;
    private int vitesse;

    public Boss () {
        this.pv = 60;
        this.degats = 10;
        this.vitesse = 3;

    }

    public int getPv() { return this.pv; };
    public int getDegats() { return this.degats;};

}
