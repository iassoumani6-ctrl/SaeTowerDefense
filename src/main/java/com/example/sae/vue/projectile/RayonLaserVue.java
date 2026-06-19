package com.example.sae.vue.projectile;

import com.example.sae.modele.projectile.RayonLaser;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class RayonLaserVue {

    private RayonLaser rayon;
    private Line ligne;
    private Pane paneJeu;

    public RayonLaserVue(RayonLaser rayon, Pane paneJeu) {
        this.rayon = rayon;
        this.paneJeu = paneJeu;

        this.ligne = new Line(
                rayon.getDepartX(),
                rayon.getDepartY(),
                rayon.getArriveeX(),
                rayon.getArriveeY()
        );

        ligne.setStroke(Color.RED);
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