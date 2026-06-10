package com.example.sae.vue.listener;
import com.example.sae.modele.Ballon;
import com.example.sae.vue.BallonVue;
import javafx.collections.ListChangeListener;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.Map;

public class BallonsListener implements ListChangeListener<Ballon> {
//BallonsListener écoute la liste des ballons. Quand un ballon est ajouté,
//il crée son affichage et quand un ballon est supprimé, il supprime son affichage.

    private Pane paneJeu;
    private Map<Ballon, BallonVue> ballonVueMap;

    public BallonsListener (Pane iPaneJeu, Map<Ballon, BallonVue> iBallonVueMap){

        this.paneJeu = iPaneJeu;
        this.ballonVueMap = iBallonVueMap;
    }

    @Override
    public void onChanged(Change<? extends Ballon> c) {

        while (c.next()) {

            // Si des ballons ont été ajoutés dans la liste observable
            if (c.wasAdded()) {
                for (Ballon ballon : c.getAddedSubList()) {

                    Image image = new Image(getClass().getResourceAsStream(ballon.getCheminImage()));

                    BallonVue ballonVue = new BallonVue(ballon, paneJeu, image);

                    // On garde le lien entre le ballon du modèle et sa vue
                    ballonVueMap.put(ballon, ballonVue);
                }
            }

            // Si des ballons ont été supprimés de la liste observable
            if (c.wasRemoved()) {
                for (Ballon ballon : c.getRemoved()) {

                    BallonVue ballonVue = ballonVueMap.get(ballon);

                    if (ballonVue != null) {
                        ballonVue.supprimer();
                        ballonVueMap.remove(ballon);
                    }
                }
            }
        }



    }
}
