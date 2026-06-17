package com.example.sae.vue;

import com.example.sae.modele.Ballon;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import com.example.sae.vue.listener.PvBallonListener;

public class BallonVue {

    private Ballon ballon;
    private ImageView imageView;
    private Pane paneJeu;
    private Rectangle hp;


    public BallonVue(Ballon iBallon, Pane paneJeu, Image image) {
        this.ballon = iBallon;
        this.paneJeu = paneJeu;

        this.imageView = new ImageView(image);

        int taille = ballon.getTailleAffichage();

        this.imageView.setFitWidth(taille);
        this.imageView.setFitHeight(taille);

        this.imageView.layoutXProperty().bind(ballon.xProperty().subtract(taille / 2.0));
        this.imageView.layoutYProperty().bind(ballon.yProperty().subtract(taille / 2.0));

        this.hp = new Rectangle();

        double largeurBarreVie = taille * 1.2;

        this.hp.setWidth(largeurBarreVie);
        this.hp.setHeight(4);
        this.hp.setFill(Color.GREEN);

        ballon.pvProperty().addListener(
                new PvBallonListener(hp, ballon.getPvMax(), largeurBarreVie)
        );

        this.hp.translateXProperty().bind(
                imageView.layoutXProperty().add((taille - largeurBarreVie) / 2.0)
        );

        this.hp.translateYProperty().bind(
                imageView.layoutYProperty().subtract(6)
        );

        this.paneJeu.getChildren().addAll(imageView, hp);
    }

    public void supprimer() {
        paneJeu.getChildren().removeAll(imageView, hp);
    }

    public Ballon getBallon() {
        return ballon;
    }
}