package com.example.sae.vue;

import com.example.sae.modele.Tour;
import com.example.sae.modele.defenseurs.Laser;
import com.example.sae.modele.projectile.Rayon_Laser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.effect.Bloom;
import javafx.scene.transform.Rotate;

public class LaserVue {

    private final Laser laser;
    private final Pane  paneJeu;

    private Line      rayonLine;
    private ImageView imageRayon;

    public LaserVue(Laser laser, Pane paneJeu) {
        this.laser   = laser;
        this.paneJeu = paneJeu;

        // Sprite de la tour
        Image imgTour = new Image(
                getClass().getResourceAsStream(laser.getCheminImage()));
        ImageView ivTour = new ImageView(imgTour);
        ivTour.setFitWidth(Tour.TAILLE_CASES  * 32);
        ivTour.setFitHeight(Tour.TAILLE_CASES * 32);
        ivTour.setLayoutX(laser.getPixelX());
        ivTour.setLayoutY(laser.getPixelY());
        paneJeu.getChildren().add(ivTour);
    }

    /** À appeler à chaque frame dans ta boucle de jeu */
    public void update() {
        Rayon_Laser attaque = laser.getAttaqueActive();

        if (attaque == null) {
            supprimerRayon();
            return;
        }

        // Fade-out : opacité diminue au fil du temps
        double opacite = 1.0 - attaque.getProgression();

        if (rayonLine == null) {
            creerRayon(attaque);
        }

        rayonLine.setOpacity(opacite);
        if (imageRayon != null) imageRayon.setOpacity(opacite);

        if (attaque.isTerminee()) {
            supprimerRayon();
        }
    }

    private void creerRayon(Rayon_Laser attaque) {
        // Trait cyan de secours
        rayonLine = new Line(
                attaque.getSourceX(), attaque.getSourceY(),
                attaque.getCibleX(),  attaque.getCibleY()
        );
        rayonLine.setStroke(Color.CYAN);
        rayonLine.setStrokeWidth(3);
        rayonLine.setEffect(new Bloom(0.3));
        paneJeu.getChildren().add(rayonLine);

        // PNG orienté correctement
        try {
            Image imgLaser = new Image(
                    getClass().getResourceAsStream(
                            "/com/example/sae/image/laser_attaque.png"));

            double dx       = attaque.getCibleX() - attaque.getSourceX();
            double dy       = attaque.getCibleY() - attaque.getSourceY();
            double longueur = Math.sqrt(dx * dx + dy * dy);
            double angleDeg = Math.toDegrees(Math.atan2(dy, dx));

            imageRayon = new ImageView(imgLaser);
            imageRayon.setFitWidth(longueur);
            imageRayon.setFitHeight(8);
            imageRayon.setPreserveRatio(false);

            // ✅ On place le coin supérieur gauche à la source
            // en décalant de moitié la hauteur vers le haut
            imageRayon.setLayoutX(attaque.getSourceX());
            imageRayon.setLayoutY(attaque.getSourceY() - 4);

            // ✅ Pivot sur le point SOURCE (x=0, y=4 = milieu hauteur)
            // et non sur le centre de l'image
            Rotate rotation = new Rotate(angleDeg, 0, 4);
            imageRayon.getTransforms().add(rotation);

            paneJeu.getChildren().add(imageRayon);

        } catch (NullPointerException e) {
            imageRayon = null;
        }
    }

    private void supprimerRayon() {
        if (rayonLine != null) {
            paneJeu.getChildren().remove(rayonLine);
            rayonLine = null;
        }
        if (imageRayon != null) {
            paneJeu.getChildren().remove(imageRayon);
            imageRayon = null;
        }
    }
}