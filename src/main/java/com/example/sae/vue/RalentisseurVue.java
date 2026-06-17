package com.example.sae.vue;

import com.example.sae.modele.Tour;
import com.example.sae.modele.defenseurs.Ralentisseur;
import com.example.sae.modele.projectile.Rayon_Tornade;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;

public class RalentisseurVue {

    private static final double TAILLE_TORNADE = 40;

    private final Ralentisseur ralentisseur;
    private final Pane         paneJeu;

    private ImageView imageTornade;
    private Rotate    rotation;
    private double    angleActuel;

    public RalentisseurVue(Ralentisseur ralentisseur, Pane paneJeu) {
        this.ralentisseur = ralentisseur;
        this.paneJeu      = paneJeu;

        // Sprite de la tour
        Image imgTour = new Image(
                getClass().getResourceAsStream(ralentisseur.getCheminImage()));
        ImageView ivTour = new ImageView(imgTour);
        ivTour.setFitWidth(Tour.TAILLE_CASES  * 32);
        ivTour.setFitHeight(Tour.TAILLE_CASES * 32);
        ivTour.setLayoutX(ralentisseur.getPixelX());
        ivTour.setLayoutY(ralentisseur.getPixelY());
        paneJeu.getChildren().add(ivTour);
    }

    /** À appeler à chaque frame dans la boucle de jeu. */
    public void update() {
        Rayon_Tornade attaque = ralentisseur.getAttaqueActive();

        if (attaque == null) {
            supprimerTornade();
            return;
        }

        // Créer le sprite au premier frame de l'attaque
        if (imageTornade == null) {
            creerTornade(attaque);
        }

        if (imageTornade != null) {
            // Position interpolée : la tornade vole vers l'ennemi
            double x = attaque.getXActuel();
            double y = attaque.getYActuel();
            imageTornade.setLayoutX(x - TAILLE_TORNADE / 2);
            imageTornade.setLayoutY(y - TAILLE_TORNADE / 2);

            // Rotation sur elle-même à chaque frame → effet tourbillon
            angleActuel += 15;
            rotation.setAngle(angleActuel);
        }

        if (attaque.isTerminee()) {
            supprimerTornade();
        }
    }

    private void creerTornade(Rayon_Tornade attaque) {
        try {
            Image imgTornade = new Image(
                    getClass().getResourceAsStream("/com/example/sae/image/attaque_tour/tornade.png"));

            if (imgTornade.isError()) {
                System.err.println("[RalentisseurVue] tornade.png introuvable !");
                return;
            }

            imageTornade = new ImageView(imgTornade);
            imageTornade.setFitWidth(TAILLE_TORNADE);
            imageTornade.setFitHeight(TAILLE_TORNADE);
            imageTornade.setPreserveRatio(true);

            // Position initiale : centrée sur la source
            imageTornade.setLayoutX(attaque.getSourceX() - TAILLE_TORNADE / 2);
            imageTornade.setLayoutY(attaque.getSourceY() - TAILLE_TORNADE / 2);

            // Pivot au centre de l'image pour la rotation sur elle-même
            angleActuel = 0;
            rotation = new Rotate(0, TAILLE_TORNADE / 2, TAILLE_TORNADE / 2);
            imageTornade.getTransforms().add(rotation);

            paneJeu.getChildren().add(imageTornade);

        } catch (NullPointerException e) {
            System.err.println("[RalentisseurVue] NullPointer : tornade.png absent du classpath");
            imageTornade = null;
        }
    }

    private void supprimerTornade() {
        if (imageTornade != null) {
            paneJeu.getChildren().remove(imageTornade);
            imageTornade = null;
            rotation     = null;
            angleActuel  = 0;
        }
    }
}
