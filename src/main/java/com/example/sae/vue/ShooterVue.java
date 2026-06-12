package com.example.sae.vue;
import com.example.sae.modele.Tour;
import com.example.sae.modele.defenseurs.Shooter;
import com.example.sae.modele.projectile.Rayon_Laser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.effect.Bloom;
import javafx.scene.transform.Rotate;

public class ShooterVue {
    private final Shooter shooter;
    private final Pane  paneJeu;

    private Line      rayonLine;
    private ImageView imageRayon;

    public ShooterVue(Shooter shooter, Pane paneJeu) {
        this.shooter   = shooter;
        this.paneJeu = paneJeu;

        Image imgTour = new Image(getClass().getResourceAsStream(shooter.getCheminImage()));
        ImageView ivTour = new ImageView(imgTour);
        ivTour.setFitWidth(Tour.TAILLE_CASES  * 32);
        ivTour.setFitHeight(Tour.TAILLE_CASES * 32);
        ivTour.setLayoutX(shooter.getPixelX());
        ivTour.setLayoutY(shooter.getPixelY());
        paneJeu.getChildren().add(ivTour);
    }

    public void update() {
        Rayon_Laser attaque = shooter.getAttaqueActive();

        if (attaque == null) {
            supprimerRayon();
            return;
        }

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
        rayonLine = new Line(
                attaque.getSourceX(), attaque.getSourceY(),
                attaque.getCibleX(),  attaque.getCibleY()
        );
        rayonLine.setStroke(Color.DARKRED);
        rayonLine.setStrokeWidth(3);
        rayonLine.setEffect(new Bloom(0.3));
        paneJeu.getChildren().add(rayonLine);

        try {
            Image imgLaser = new Image(
                    getClass().getResourceAsStream("/com/example/sae/image/attaque_tour/rayon_laser.png"));

            double dx       = attaque.getCibleX() - attaque.getSourceX();
            double dy       = attaque.getCibleY() - attaque.getSourceY();
            double longueur = Math.sqrt(dx * dx + dy * dy);
            double angleDeg = Math.toDegrees(Math.atan2(dy, dx));

            imageRayon = new ImageView(imgLaser);
            imageRayon.setFitWidth(longueur);
            imageRayon.setFitHeight(8);
            imageRayon.setPreserveRatio(false);

            // ✅ Coin supérieur gauche positionné à la source
            imageRayon.setLayoutX(attaque.getSourceX());
            imageRayon.setLayoutY(attaque.getSourceY() - 4);

            // ✅ Pivot au point source (x=0, y=4 = milieu de la hauteur 8px)
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


