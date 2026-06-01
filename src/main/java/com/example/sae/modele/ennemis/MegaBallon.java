package com.example.sae.modele.ennemis;

import com.example.sae.modele.Ballon;

public class MegaBallon extends Ballon {
    public MegaBallon() {
        super(200,40);
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/Anim_Ballon/Animation_ballon_BOSS.gif";
    }
}
