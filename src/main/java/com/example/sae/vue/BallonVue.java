package com.example.sae.vue;

import com.example.sae.modele.Ballon;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class BallonVue {

    private Ballon ballon;
    private ImageView imageView;
    private Pane paneJeu;

    public BallonVue(Ballon ballon, Pane paneJeu, Image image) {
        this.ballon = ballon;
        this.paneJeu = paneJeu;

        this.imageView = new ImageView(image);
        paneJeu.getChildren().add(imageView);

    }

    public void mettreAJourPosition() {
        imageView.setLayoutX(ballon.getX() - 16);
        imageView.setLayoutY(ballon.getY() - 16);
    }

    public Ballon getBallon() {
        return this.ballon;
    }
}
