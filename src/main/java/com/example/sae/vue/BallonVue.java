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

        this.imageView.layoutXProperty().bind(ballon.xProperty().subtract(16));
        this.imageView.layoutYProperty().bind(ballon.yProperty().subtract(16));

        this.hp = new Rectangle();
        this.hp.setWidth(45);
        this.hp.setHeight(3);
        this.hp.setFill(Color.GREEN);

        ballon.pvProperty().addListener(new PvBallonListener(hp, ballon.getPvMax(), 45));

        this.hp.translateXProperty().bind(imageView.layoutXProperty());
        this.hp.translateYProperty().bind(imageView.layoutYProperty().subtract(5));

        this.paneJeu.getChildren().addAll(imageView, hp);
    }

    public void supprimer() {
        paneJeu.getChildren().removeAll(imageView, hp);
    }

    public Ballon getBallon() {
        return ballon;
    }
}