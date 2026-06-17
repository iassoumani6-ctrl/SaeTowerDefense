package com.example.sae.vue.defenseursVue;

import com.example.sae.modele.defenseurs.Zoner;
import com.example.sae.vue.TourVue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ZonerVue extends TourVue {

    private Zoner zoner;

    private Image[] imagesRepos;
    private Image[] imagesAttaque;

    private Timeline animation;
    private boolean animationAttaque;

    public ZonerVue(Zoner zoner, Pane paneJeu) {
        super(zoner, paneJeu, "/com/example/sae/image/ZonerAnim/ZonerRepos1.png");

        this.zoner = zoner;
        this.imagesRepos = chargerImages("ZonerRepos");
        this.imagesAttaque = chargerImages("ZonerAtt");
        this.animationAttaque = false;

        lancerAnimation(imagesRepos, false);

        zoner.enAttaqueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                lancerAnimation(imagesAttaque, true);
            } else {
                lancerAnimation(imagesRepos, false);
            }
        });
    }

    private Image[] chargerImages(String prefixe) {
        Image[] images = new Image[7];

        for (int i = 0; i < images.length; i++) {
            images[i] = new Image(getClass().getResourceAsStream(
                    "/com/example/sae/image/ZonerAnim/" + prefixe + (i + 1) + ".png"
            ));
        }

        return images;
    }

    private void lancerAnimation(Image[] images, boolean attaque) {
        System.out.println("maj anim avec attaque = " + attaque);
        if (animation != null && animationAttaque == attaque) {
            return;
        }

        if (animation != null) {
            animation.stop();
        }

        animationAttaque = attaque;
        animation = new Timeline();

        for (int i = 0; i < images.length; i++) {
            final int indice = i;

            animation.getKeyFrames().add(
                    new KeyFrame(Duration.millis(100 * i), event -> {
                        imageView.setImage(images[indice]);
                    })
            );
        }

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }
}