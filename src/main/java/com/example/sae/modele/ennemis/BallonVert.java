package com.example.sae.modele.ennemis;

import com.example.sae.modele.Ballon;

public class BallonVert extends Ballon {
    public BallonVert() {
        super(50,5);
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Anim_Ballon/Animation_ballon_vert.gif";
    }
}
