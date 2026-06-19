package com.example.sae.vue.projectile;

import com.example.sae.modele.projectile.RayonTornade;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class RayonTornadeVue {

    private RayonTornade rayon;
    private Line ligne;
    private Pane paneJeu;

    public RayonTornadeVue(RayonTornade rayon, Pane paneJeu) {
        this.rayon = rayon;
        this.paneJeu = paneJeu;

        this.ligne = new Line(
                rayon.getDepartX(),
                rayon.getDepartY(),
                rayon.getArriveeX(),
                rayon.getArriveeY()
        );

        ligne.setStroke(Color.CYAN);
        ligne.setStrokeWidth(4);
        ligne.setMouseTransparent(true);

        paneJeu.getChildren().add(ligne);
    }

    public void mettreAJour() {
        ligne.setStartX(rayon.getDepartX());
        ligne.setStartY(rayon.getDepartY());
        ligne.setEndX(rayon.getArriveeX());
        ligne.setEndY(rayon.getArriveeY());
    }

    public void supprimer() {
        paneJeu.getChildren().remove(ligne);
    }
}