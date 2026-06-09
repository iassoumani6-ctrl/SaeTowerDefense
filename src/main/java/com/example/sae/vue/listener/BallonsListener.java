package com.example.sae.vue.listener;
import com.example.sae.modele.Ballon;
import com.example.sae.vue.BallonVue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;

import java.util.Map;

public class BallonsListener implements ListChangeListener<Ballon> {

    private Pane paneJeu;
    private Map<Ballon, BallonVue> ballonVueMap;

    public BallonsListener (Pane iPaneJeu, Map<Ballon, BallonVue> iBallonVueMap){

        this.paneJeu = iPaneJeu;
        this.ballonVueMap = iBallonVueMap;
    }

    @Override
    public void onChanged(Change<? extends Ballon> change) {



    }
}
