package com.example.sae.modele.ennemis;

import com.example.sae.modele.Ballon;

public class BallonJaune extends Ballon {

    public BallonJaune() {
        super(60, 10, 1.2, 15);;
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Anim_Ballon/Animation_ballon_jaune.gif";
    }
}