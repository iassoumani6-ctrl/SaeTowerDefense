package com.example.sae.modele.ennemis;

import com.example.sae.modele.Ballon;

public class BallonOrange extends Ballon {

    public BallonOrange() {
        super(70, 15, 1.0);
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Anim_Ballon/Animation_ballon_orange.gif";
    }
}