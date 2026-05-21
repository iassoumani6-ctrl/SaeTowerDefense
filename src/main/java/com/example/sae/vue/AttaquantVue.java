package com.example.sae.vue;

import com.example.sae.modele.Attaquant;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class AttaquantVue {

    private Attaquant attaquant;
    private ImageView imageView;
    private Pane paneJeu;

    public AttaquantVue(Attaquant attaquant, Pane paneJeu, Image image) {
        this.attaquant = attaquant;
        this.paneJeu = paneJeu;

        this.imageView = new ImageView(image);
        paneJeu.getChildren().add(imageView);

    }

    public void mettreAJourPosition() {
        imageView.setLayoutX(attaquant.getX()+ 16);
        imageView.setLayoutY(attaquant.getY()+ 16);
    }
}
