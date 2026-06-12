package com.example.sae.vue;

import com.example.sae.modele.Tour;
import com.example.sae.modele.defenseurs.Canonner;
import com.example.sae.modele.projectile.Balle_en_feu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CanonnerVue {

    private final Canonner canonner;
    private final Pane     paneJeu;

    private final Map<Balle_en_feu, ImageView> balleVues = new HashMap<>();
    private final Image imageBalleBasket;

    public CanonnerVue(Canonner canonner, Pane paneJeu) {
        this.canonner = canonner;
        this.paneJeu  = paneJeu;

        Image imgTour = new Image(
                getClass().getResourceAsStream(canonner.getCheminImage()));
        ImageView ivTour = new ImageView(imgTour);
        ivTour.setFitWidth(Tour.TAILLE_CASES  * 32);
        ivTour.setFitHeight(Tour.TAILLE_CASES * 32);
        ivTour.setLayoutX(canonner.getPixelX());
        ivTour.setLayoutY(canonner.getPixelY());
        paneJeu.getChildren().add(ivTour);

        imageBalleBasket = new Image(
                getClass().getResourceAsStream(
                        "/com/example/sae/image/attaque_tour/balle_en_feu.png"));
    }

    public void update() {
        List<Balle_en_feu> ballesActuelles = canonner.getBallesEnVol();

        // Ajoute les nouvelles balles
        for (Balle_en_feu balle : ballesActuelles) {
            if (!balleVues.containsKey(balle)) {
                ImageView iv = new ImageView(imageBalleBasket);
                iv.setFitWidth(24);
                iv.setFitHeight(24);
                paneJeu.getChildren().add(iv);
                balleVues.put(balle, iv);
            }
        }

        // Met à jour la position
        for (Map.Entry<Balle_en_feu, ImageView> entry : balleVues.entrySet()) {
            Balle_en_feu balle = entry.getKey();
            ImageView   iv    = entry.getValue();
            iv.setLayoutX(balle.getX() - 12);
            iv.setLayoutY(balle.getY() - 12);
        }

        // Supprime les balles arrivées
        List<Balle_en_feu> aSupprimer = new ArrayList<>();
        for (Balle_en_feu balle : balleVues.keySet()) {
            if (!ballesActuelles.contains(balle)) {
                paneJeu.getChildren().remove(balleVues.get(balle));
                aSupprimer.add(balle);
            }
        }
        aSupprimer.forEach(balleVues::remove);
    }
}