package com.example.sae.vue;

import com.example.sae.modele.Partie;
import com.example.sae.vue.listener.ArgentJoueurListener;
import com.example.sae.vue.listener.PvJoueurListener;
import javafx.scene.control.Label;

public class PartieVue {

    private Partie partie;
    private Label pvLabel;
    private Label piecesLabel;

    public PartieVue(Partie iPartie, Label iPvLabel, Label iPiecesLabel) {
        this.partie = iPartie;
        this.pvLabel = iPvLabel;
        this.piecesLabel = iPiecesLabel;

        initialiserBindings();
        initialiserListeners();
    }

    private void initialiserBindings() {
        pvLabel.textProperty().bind(partie.pvJoueurProperty().asString());
        piecesLabel.textProperty().bind(partie.argentJoueurProperty().asString());
    }

    private void initialiserListeners() {
        partie.pvJoueurProperty().addListener(new PvJoueurListener(150));
        partie.argentJoueurProperty().addListener(new ArgentJoueurListener());
    }
}