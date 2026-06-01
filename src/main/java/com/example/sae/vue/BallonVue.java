package com.example.sae.vue;

import com.example.sae.modele.Ballon;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BallonVue {

    private Ballon ballon;
    private ImageView imageView;
    private Pane paneJeu;
    private Rectangle hp;

    public BallonVue(Ballon iBallon, Pane paneJeu, Image image) {
        this.ballon = iBallon;
        this.paneJeu   = paneJeu;
        this.imageView = new ImageView(image);
        this.hp = new Rectangle();
        this.hp.setWidth(45);
        this.hp.setHeight(3);
        this.hp.setFill(Color.GREEN);
        this.hp.translateXProperty().bind(imageView.layoutXProperty());
        this.hp.translateYProperty().bind(imageView.layoutYProperty().subtract(5));
        this.paneJeu.getChildren().addAll(imageView, hp);
            }




    public void mettreAJourPosition() {
        imageView.setLayoutX(ballon.getX() - 16);
        imageView.setLayoutY(ballon.getY() - 16);
    }

    public void supprimer() {
        paneJeu.getChildren().remove(imageView);
    }

    public Ballon getBallon() { return ballon; }
}
