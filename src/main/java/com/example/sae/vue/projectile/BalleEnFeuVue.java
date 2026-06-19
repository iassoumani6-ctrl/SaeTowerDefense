package com.example.sae.vue.projectile;

import com.example.sae.modele.projectile.BalleEnFeu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class BalleEnFeuVue {

    private BalleEnFeu balle;
    private ImageView imageView;
    private Pane paneJeu;

    public BalleEnFeuVue(BalleEnFeu balle, Pane paneJeu) {
        this.balle = balle;
        this.paneJeu = paneJeu;

        Image image = new Image(getClass().getResourceAsStream(
                "/com/example/sae/image/attaque_tour/BalleEnFeu.png"
        ));

        this.imageView = new ImageView(image);
        this.imageView.setFitWidth(24);
        this.imageView.setFitHeight(24);

        paneJeu.getChildren().add(imageView);
        mettreAJour();
    }

    public void mettreAJour() {
        imageView.setLayoutX(balle.getX() - 12);
        imageView.setLayoutY(balle.getY() - 12);
    }

    public void supprimer() {
        paneJeu.getChildren().remove(imageView);
    }
}