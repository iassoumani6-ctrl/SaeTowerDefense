package com.example.sae.vue;

import com.example.sae.modele.Partie;
import com.example.sae.vue.listener.ArgentJoueurListener;
import com.example.sae.vue.listener.PvJoueurListener;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PartieVue {

    private Partie partie;
    private ImageView coeursVieImageView;
    private ImageView coinImageView;
    private Label piecesLabel;

    public PartieVue(Partie iPartie, ImageView iCoeursVieImageView, ImageView iCoinImageView, Label iPiecesLabel) {
        this.partie = iPartie;
        this.coeursVieImageView = iCoeursVieImageView;
        this.coinImageView = iCoinImageView;
        this.piecesLabel = iPiecesLabel;

        initialiserImages();
        initialiserBindings();
        initialiserListeners();
    }

    private void initialiserImages() {
        Image imageCoin = new Image(getClass().getResourceAsStream("/com/example/sae/image/Coin.gif"));
        coinImageView.setImage(imageCoin);
    }

    private void initialiserBindings() {
        piecesLabel.textProperty().bind(partie.argentJoueurProperty().asString());
    }

    private void initialiserListeners() {
        partie.pvJoueurProperty().addListener(
                new PvJoueurListener(partie.getPvJoueur(), coeursVieImageView)
        );

        partie.argentJoueurProperty().addListener(new ArgentJoueurListener());
    }
}