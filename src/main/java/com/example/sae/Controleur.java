package com.example.sae;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.GestionnaireVagues;
import com.example.sae.modele.Terrain;
import com.example.sae.modele.Tour;
import com.example.sae.modele.defenseurs.*;
import com.example.sae.modele.ennemis.*;
import com.example.sae.vue.BallonVue;
import com.example.sae.vue.TerrainVue;
import com.example.sae.vue.TourVue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controleur implements Initializable {

    @FXML private Pane  paneJeu;
    @FXML private Label selectionLabel;
    @FXML private Label vagueLabel;
    @FXML private Label piècesLabel;

    private GestionnaireVagues gestionnaireVagues;
    private Terrain terrain;
    private TerrainVue terrainVue;

    private final List<Tour>      tours      = new ArrayList<>();
    private final List<BallonVue> ballonVues = new ArrayList<>();

    private int colSelectionnee   = -1;
    private int ligneSelectionnee = -1;
    private int pieces = 500;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        terrain = new Terrain();
        terrainVue = new TerrainVue(terrain, paneJeu);
        terrainVue.dessinerTerrain();
        gestionnaireVagues = new GestionnaireVagues();

        ajouterEnnemi(new BallonVert());

        if (piècesLabel != null) piècesLabel.setText(String.valueOf(pieces));

        paneJeu.setOnMouseClicked(event -> gererClicSurTerrain(event.getX(), event.getY()));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> tick()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void gererClicSurTerrain(double pixelX, double pixelY) {
        int colonne = (int)(pixelX / Terrain.TAILLE_CASE);
        int ligne   = (int)(pixelY / Terrain.TAILLE_CASE);

        boolean valide = terrain.peutPlacerTour(ligne, colonne, Tour.TAILLE_CASES);
        terrainVue.afficherSelection(ligne, colonne, valide);

        if (valide) {
            colSelectionnee   = colonne;
            ligneSelectionnee = ligne;
        } else {
            colSelectionnee   = -1;
            ligneSelectionnee = -1;
        }
    }

    private void tick() {
        Ballon nouveau = gestionnaireVagues.tick();
        if (nouveau != null) ajouterEnnemi(nouveau);

        if (gestionnaireVagues.isVagueEnCours() && gestionnaireVagues.isFileSpawnVide() && ballonVues.isEmpty()) {
            gestionnaireVagues.signalerVagueFinie();
        }

        if (vagueLabel != null) vagueLabel.setText(String.valueOf(gestionnaireVagues.getNumeroVague()));

        for (BallonVue av : new ArrayList<>(ballonVues)) {
            av.getBallon().avancer();
        }

        List<Ballon> ballons = ballonVues.stream().map(BallonVue::getBallon).collect(Collectors.toList());

        for (Tour tour : tours) {
            if (tour instanceof Zoner) {
                ((Zoner) tour).tirerSurTous(ballons);
            } else if (tour instanceof Canonner) {
                for (Ballon b : ballons) {
                    if (!b.estMort()) {
                        if (((Canonner) tour).tirerCanon(b, ballons)) break;
                    }
                }
            } else if (tour instanceof Laser) {
                for (Ballon b : ballons) {
                    if (!b.estMort()) {
                        if (((Laser) tour).tirerLaser(b)) break;
                    }
                }
            } else {
                for (Ballon b : ballons) {
                    if (!b.estMort()) {
                        if (tour.tirerSur(b)) break;
                    }
                }
            }
        }

        Iterator<BallonVue> it = ballonVues.iterator();
        while (it.hasNext()) {
            BallonVue av = it.next();
            if (av.getBallon().estMort()) {
                av.supprimer();
                it.remove();
            }
        }
    }

    private void placerTour(Tour tour) {
        if (colSelectionnee == -1 || ligneSelectionnee == -1) {
            System.out.println("Sélectionnez une case valide d'abord !");
            return;
        }
        if (!terrain.peutPlacerTour(ligneSelectionnee, colSelectionnee, Tour.TAILLE_CASES)) {
            System.out.println("Placement invalide !");
            return;
        }
        if (gestionnaireVagues.getNumeroVague() < tour.getVagueDeblocage()) {
            System.out.println("Tour non encore débloquée (vague " + tour.getVagueDeblocage() + " requise) !");
            return;
        }
        if (pieces < tour.getCout()) {
            System.out.println("Pas assez de pièces !");
            return;
        }
        pieces -= tour.getCout();
        if (piècesLabel != null) piècesLabel.setText(String.valueOf(pieces));

        terrain.occuperCasesTour(tour);
        tours.add(tour);
        new TourVue(tour, paneJeu);

        colSelectionnee   = -1;
        ligneSelectionnee = -1;
        terrainVue.cacherSelection();
    }

    @FXML private void ajouterTourShifty()       { placerTour(new Shifty(colSelectionnee, ligneSelectionnee)); }
    @FXML private void ajouterTourShooter()      { placerTour(new Shooter(colSelectionnee, ligneSelectionnee)); }
    @FXML private void ajouterTourLaser()        { placerTour(new Laser(colSelectionnee, ligneSelectionnee)); }
    @FXML private void ajouterTourZoner()        { placerTour(new Zoner(colSelectionnee, ligneSelectionnee)); }
    @FXML private void ajouterTourRalentisseur() { placerTour(new Ralentisseur(colSelectionnee, ligneSelectionnee)); }
    @FXML private void ajouterTourCanonner()     { placerTour(new Canonner(colSelectionnee, ligneSelectionnee)); }

    @FXML
    private void spawnerEnnemi() {
        Ballon[] types = { new BallonVert(), new BallonRouge(), new BallonJaune(), new BallonOrange(), new MegaBallon() };
        ajouterEnnemi(types[(int)(Math.random() * types.length)]);
    }

    private void ajouterEnnemi(Ballon ennemi) {
        Image imageEnnemi = new Image(Main.class.getResourceAsStream(ennemi.getCheminImage()));
        BallonVue av = new BallonVue(ennemi, paneJeu, imageEnnemi);

        ballonVues.add(av);
    }
}