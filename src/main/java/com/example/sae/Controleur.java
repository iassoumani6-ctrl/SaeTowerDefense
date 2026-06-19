package com.example.sae;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Terrain;
import com.example.sae.modele.Tour;
import com.example.sae.modele.defenseurs.*;
import com.example.sae.modele.ennemis.*;
import com.example.sae.vue.*;
import com.example.sae.vue.defenseursVue.TourVueFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.HashMap;
import java.util.Map;
import com.example.sae.vue.listener.BallonsListener;
import com.example.sae.modele.GestionnaireVagues;
import com.example.sae.modele.Partie;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import com.example.sae.modele.projectile.BalleEnFeu;
import com.example.sae.modele.projectile.RayonLaser;
import com.example.sae.modele.projectile.RayonTornade;
import com.example.sae.vue.projectile.BalleEnFeuVue;
import com.example.sae.vue.projectile.RayonLaserVue;
import com.example.sae.vue.projectile.RayonTornadeVue;

public class Controleur implements Initializable {

    @FXML private Pane  paneJeu;
    @FXML private Label selectionLabel;
    @FXML private Button boutonVitesseJeu;
    @FXML private Label piecesLabel;
    @FXML private Label vagueLabel;
    @FXML private ImageView coeursVieImageView;
    @FXML private ImageView coinImageView;

    @FXML private BorderPane gameRoot;
    @FXML private VBox menuCartesPane;
    @FXML private Button boutonJouerMap1;
    @FXML private Label mapSelectionLabel;

    private Terrain terrain;
    private TerrainVue terrainVue;
    private GestionnaireVagues gestionnaireVagues;
    private Partie partie;
    private PartieVue partieVue;

    private String mapSelectionnee = null;

    private Timeline timeline;
    private final double[] vitessesJeu = {1.0, 2.0, 2.5};
    private int indiceVitesseJeu = 0;

    private final List<Tour>         tours         = new ArrayList<>();
    private final ObservableList<Ballon> ballons = FXCollections.observableArrayList();
    private final Map<Ballon, BallonVue> ballonVueMap = new HashMap<>();
    private final Map<Tour, TourVue> tourVueMap = new HashMap<>();

    private final Map<BalleEnFeu, BalleEnFeuVue> balleEnFeuVueMap = new HashMap<>();
    private final Map<RayonLaser, RayonLaserVue> rayonLaserVueMap = new HashMap<>();
    private final Map<RayonTornade, RayonTornadeVue> rayonTornadeVueMap = new HashMap<>();

    private int compteurGainArgent = 0;
    private static final int tickGainArgent = 300;
    private static final int argentPassif = 5;

    private int colSelectionnee  = -1; // -1 = aucune case selectionnée
    private int ligneSelectionnee = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        terrain = new Terrain();
        terrainVue = new TerrainVue(terrain, paneJeu);
        terrainVue.dessinerTerrain();

        boutonJouerMap1.setVisible(false);
        boutonJouerMap1.setManaged(false);

        mapSelectionLabel.setText("Clique sur une map");

        partie = new Partie(150, 100);
        partieVue = new PartieVue(partie, coeursVieImageView, coinImageView, piecesLabel);

        ballons.addListener(new BallonsListener(paneJeu, ballonVueMap));


        gestionnaireVagues = new GestionnaireVagues();


        ajouterEnnemi(new BallonVert());

        paneJeu.setOnMouseClicked(event -> gererClicSurTerrain(event.getX(), event.getY()));

        this.timeline = new Timeline(
                new KeyFrame(Duration.millis(20 ), e -> tick())
        );
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.setRate(vitessesJeu[indiceVitesseJeu]);

        gameRoot.setVisible(false);
        gameRoot.setManaged(false);

        menuCartesPane.setVisible(true);
        menuCartesPane.setManaged(true);

        boutonJouerMap1.setVisible(false);
        boutonJouerMap1.setManaged(false);

        mapSelectionLabel.setText("Clique sur une map");
    }


    private void gererClicSurTerrain(double pixelX, double pixelY) {
        int colonne = (int) (pixelX / Terrain.TAILLE_CASE);
        int ligne = (int) (pixelY / Terrain.TAILLE_CASE);

        boolean valide = terrain.peutPlacerTour(ligne, colonne, Tour.TAILLE_CASES);

        terrainVue.afficherSelection(ligne, colonne, valide);

        if (valide) {
            colSelectionnee = colonne;
            ligneSelectionnee = ligne;
            System.out.println("Case sélectionnée = " + ligne + " " + colonne);
        } else {
            colSelectionnee = -1;
            ligneSelectionnee = -1;
            System.out.println("Placement invalide");
        }
    }

    private void tick() {

        compteurGainArgent++;

        if (compteurGainArgent >= tickGainArgent) {
            partie.gagnerArgent(argentPassif);
            compteurGainArgent = 0;
        }

        Ballon ballonSpawn = gestionnaireVagues.tick();

        vagueLabel.setText("Vague " + gestionnaireVagues.getNumeroVague());

        if (ballonSpawn != null) {
            ajouterEnnemi(ballonSpawn);
        }

        for (Ballon iBallon : ballons) {
            iBallon.avancer();

        }

        // Chaque tour reçoit la liste complète des ballons : indispensable
        // pour les tours de zone (Zoner, Canonner) et à verrouillage (Laser,
        // Shifty). Chaque tour applique son propre comportement d'attaque.
        for (Tour tour : tours) {

            boolean ennemiDansPortee = false;

            for (Ballon ballon : ballons) {
                if (tour.estDansPortee(ballon)) {
                    ennemiDansPortee = true;
                    break;
                }
            }

            tour.attaquer(ballons);
            tour.setEnAttaque(ennemiDansPortee);

            if (tour instanceof Shifty) {
                ((Shifty) tour).mettreAJourVitesse(ennemiDansPortee);
            }

            if (tour instanceof Canonner) {
                ((Canonner) tour).updateProjectiles();
            }

            if (tour instanceof Laser) {
                ((Laser) tour).updateProjectiles();
            }

            if (tour instanceof Ralentisseur) {
                ((Ralentisseur) tour).updateProjectiles();
            }
        }
        mettreAJourProjectilesVisuels();

        Iterator<Ballon> itB = ballons.iterator();//Iterator sert a parcourir une liste

        while (itB.hasNext()) {
            Ballon ballon = itB.next();

            if (ballon.estMort()) {
                partie.gagnerArgent(ballon.getRecompense());
                itB.remove();
            } else if (ballon.estArrivee()) {
                partie.perdrePv(ballon.getDegats());
                itB.remove();
            }
        }

        if (gestionnaireVagues.isVagueEnCours() && gestionnaireVagues.isFileSpawnVide() && ballons.isEmpty()) {

            gestionnaireVagues.signalerVagueFinie();
        }

        if (partie.partiePerdue()) {
            afficherDefaite();
            timeline.stop();
        }
    }

    private void mettreAJourProjectilesVisuels() {
        for (Tour tour : tours) {
            if (tour instanceof Canonner) {
                afficherBallesCanonner((Canonner) tour);
            }

            if (tour instanceof Laser) {
                afficherRayonLaser((Laser) tour);
            }

            if (tour instanceof Ralentisseur) {
                afficherRayonTornade((Ralentisseur) tour);
            }
        }
    }

    private void afficherBallesCanonner(Canonner canonner) {
        for (BalleEnFeu balle : canonner.getBallesEnVol()) {
            BalleEnFeuVue vue = balleEnFeuVueMap.get(balle);

            if (vue == null) {
                vue = new BalleEnFeuVue(balle, paneJeu);
                balleEnFeuVueMap.put(balle, vue);
            }

            vue.mettreAJour();
        }

        balleEnFeuVueMap.entrySet().removeIf(entry -> {
            if (entry.getKey().isArrivee()) {
                entry.getValue().supprimer();
                return true;
            }
            return false;
        });
    }

    private void afficherRayonLaser(Laser laser) {
        RayonLaser rayon = laser.getAttaqueActive();

        rayonLaserVueMap.entrySet().removeIf(entry -> {
            if (entry.getKey().isTerminee()) {
                entry.getValue().supprimer();
                return true;
            }
            return false;
        });

        if (rayon == null) {
            return;
        }

        RayonLaserVue vue = rayonLaserVueMap.get(rayon);

        if (vue == null) {
            vue = new RayonLaserVue(rayon, paneJeu);
            rayonLaserVueMap.put(rayon, vue);
        }

        vue.mettreAJour();
    }

    private void afficherRayonTornade(Ralentisseur ralentisseur) {
        RayonTornade rayon = ralentisseur.getAttaqueActive();

        rayonTornadeVueMap.entrySet().removeIf(entry -> {
            if (entry.getKey().isTerminee()) {
                entry.getValue().supprimer();
                return true;
            }
            return false;
        });

        if (rayon == null) {
            return;
        }

        RayonTornadeVue vue = rayonTornadeVueMap.get(rayon);

        if (vue == null) {
            vue = new RayonTornadeVue(rayon, paneJeu);
            rayonTornadeVueMap.put(rayon, vue);
        }

        vue.mettreAJour();
    }

    private void afficherDefaite() {
        System.out.println("PERDU");
    }

    private void ajouterEnnemi(Ballon iB) {
        ballons.add(iB);
    }

    @FXML
    private void selectionnerMap1() {
        mapSelectionnee = "TerrainBasket";

        boutonJouerMap1.setVisible(true);
        boutonJouerMap1.setManaged(true);

        mapSelectionLabel.setText("Map sélectionnée : Terrain Basket");
    }

    @FXML
    private void lancerPartie() {
        if (mapSelectionnee == null) {
            System.out.println("Aucune map sélectionnée !");
            return;
        }

        menuCartesPane.setVisible(false);
        menuCartesPane.setManaged(false);

        gameRoot.setVisible(true);
        gameRoot.setManaged(true);

        paneJeu.setVisible(true);
        paneJeu.setManaged(true);

        timeline.play();

        System.out.println("Partie lancée sur : " + mapSelectionnee);
    }

    @FXML
    private void spawnerEnnemi() {
        Ballon[] types = {
            new BallonVert(),
            new BallonRouge(),
            new BallonJaune(),
            new BallonOrange(),
            new MegaBallon()
        };
        Ballon ennemi = types[(int)(Math.random() * types.length)];
        ajouterEnnemi(ennemi);
    }

    @FXML
    private void ajouterTour(ActionEvent event) {
        if (colSelectionnee == -1 || ligneSelectionnee == -1) {
            System.out.println("Sélectionnez une case valide d'abord !");
            return;
        }

        if (!terrain.peutPlacerTour(ligneSelectionnee, colSelectionnee, Tour.TAILLE_CASES)) {
            System.out.println("Impossible de placer la tour ici !");
            return;
        }

        String type = ((Button) event.getSource()).getText();

        int cout = coutTour(type);
        int vagueDeblocage = vagueDeblocageTour(type);

        // Déblocage par vague
        if (gestionnaireVagues.getNumeroVague() < vagueDeblocage) {
            message(type + " : débloqué à la vague " + vagueDeblocage);
            return;
        }

        // Coût en pièces
        if (!partie.peutPayer(cout)) {
            message(type + " : pas assez de pièces (" + cout + " requis)");
            return;
        }

        Tour tour = switch (type) {
            case "Shooter"      -> new Shooter(colSelectionnee, ligneSelectionnee);
            case "Zoner"        -> new Zoner(colSelectionnee, ligneSelectionnee);
            case "Ralentisseur" -> new Ralentisseur(colSelectionnee, ligneSelectionnee);
            case "Laser"        -> new Laser(colSelectionnee, ligneSelectionnee);
            case "Canonner"     -> new Canonner(colSelectionnee, ligneSelectionnee);
            default             -> new Shifty(colSelectionnee, ligneSelectionnee);
        };

        terrain.occuperCasesTour(tour);
        tours.add(tour);

        TourVue tourVue = TourVueFactory.creerTourVue(tour, paneJeu);
        tourVueMap.put(tour, tourVue);

        partie.depenserArgent(cout);

        colSelectionnee = -1;
        ligneSelectionnee = -1;

        terrainVue.cacherSelection();

        message(type + " placé(e) !");
    }

    /** Coût en pièces de chaque type de tour. */
    private int coutTour(String type) {
        return switch (type) {
            case "Shooter"      -> 10;
            case "Laser"        -> 10;
            case "Zoner"        -> 10;
            case "Ralentisseur" -> 10;
            case "Canonner"     -> 10;
            default             -> 10; // Shifty
        };
    }

    /** Vague à partir de laquelle chaque type de tour est débloqué. */
    private int vagueDeblocageTour(String type) {
        return switch (type) {
            case "Laser"        -> 1;
            case "Zoner"        -> 1;
            case "Ralentisseur" -> 1;
            case "Canonner"     -> 1;
            default             -> 1; // Shifty et Shooter
        };
    }

    private void message(String texte) {
        if (selectionLabel != null) {
            selectionLabel.setText(texte);
        }
        System.out.println(texte);
    }

    @FXML
    private void changerVitesseJeu() {
        indiceVitesseJeu++;

        if (indiceVitesseJeu >= vitessesJeu.length) {
            indiceVitesseJeu = 0;
        }

        double nouvelleVitesse = vitessesJeu[indiceVitesseJeu];

        timeline.setRate(nouvelleVitesse);

        if (nouvelleVitesse == 1.0 || nouvelleVitesse == 2.0) {
            boutonVitesseJeu.setText("x" + (int) nouvelleVitesse);
        } else {
            boutonVitesseJeu.setText("x" + nouvelleVitesse);
        }

        System.out.println("Vitesse du jeu : x" + nouvelleVitesse);
    }
}
