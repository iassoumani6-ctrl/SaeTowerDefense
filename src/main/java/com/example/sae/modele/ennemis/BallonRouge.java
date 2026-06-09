package com.example.sae.modele.ennemis;

import com.example.sae.modele.Ballon;

public class BallonRouge extends Ballon {

    public BallonRouge() {
        super(90, 30, 0.9);
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Anim_Ballon/Animation_ballon_rouge.gif";
    }
}