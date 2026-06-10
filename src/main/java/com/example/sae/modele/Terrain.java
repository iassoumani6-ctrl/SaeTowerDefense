package com.example.sae.modele;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Terrain {

    public static final int TAILLE_CASE = 32;
    public static final int LARGEUR_GRILLE = 35;
    public static final int HAUTEUR_GRILLE = 21;

    /*
     * 0 = bloque
     * 1 = chemin praticable
     * 2 = depart
     * 3 = arrivee
     *
     * Grille logique invisible : 21 lignes x 35 colonnes.
     * Elle ne sert pas a afficher la map c'est juste la logique derriere celle ci rien a voir avec l'affichage ou le design.
     * Elle sert uniquement au deplacement (plus tard aussi au placement des tours) et au BFS.
     */
    private int[][] carteLogique = {

            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},

            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {2,1,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0},

            {0,0,0,1,1,1,1,1,1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,0,0,0,0},

            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0},

            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,3},
            {2,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0},

            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},

            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };

    private boolean[][] casesOccupeesParTour = new boolean[HAUTEUR_GRILLE][LARGEUR_GRILLE];

    public int getLargeurPixels() {
        return LARGEUR_GRILLE * TAILLE_CASE;
    }

    public int getHauteurPixels() {
        return HAUTEUR_GRILLE * TAILLE_CASE;
    }

    public int getLargeurGrille() {
        return LARGEUR_GRILLE;
    }

    public int getHauteurGrille() {
        return HAUTEUR_GRILLE;
    }

    public int getCodeLogique(int ligne, int colonne) {
        return carteLogique[ligne][colonne];
    }

    public boolean estPraticable(int ligne, int colonne) {
        if (ligne < 0 || ligne >= HAUTEUR_GRILLE || colonne < 0 || colonne >= LARGEUR_GRILLE) {
            return false;
        }

        return carteLogique[ligne][colonne] == 1
                || carteLogique[ligne][colonne] == 2
                || carteLogique[ligne][colonne] == 3;
    }

    public boolean estDansGrille(int ligne, int colonne) {
        return ligne >= 0
                && ligne < HAUTEUR_GRILLE
                && colonne >= 0
                && colonne < LARGEUR_GRILLE;
    }


    public void occuperCasesTour(Tour tour) {
        for (int l = tour.getLigne(); l < tour.getLigne() + tour.getTailleCases(); l++) {
            for (int c = tour.getColonne(); c < tour.getColonne() + tour.getTailleCases(); c++) {
                casesOccupeesParTour[l][c] = true;
            }
        }
    }

    public boolean peutPlacerTour(int ligne, int colonne, int tailleCases) {

        if (!estDansGrille(ligne, colonne)) {
            return false;
        }

        // Vérifie que la tour ne dépasse pas de la map
        // Comme une tour fait plusieurs cases, il faut vérifier sa taille complete
        if (ligne + tailleCases > HAUTEUR_GRILLE || colonne + tailleCases > LARGEUR_GRILLE) {
            return false;
        }



        // Parcourt toutes les cases que la tour va occuper
        // Exemple : si tailleCases = 2 on vérifie les 4 cases de la tour
        for (int l = ligne; l < ligne + tailleCases; l++) {
            for (int c = colonne; c < colonne + tailleCases; c++) {

                if (estPraticable(l, c)  || casesOccupeesParTour[l][c]) {
                    return false;
                }
            }
        }
        return true;
    }


    public List<int[]> trouverChemin(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee) {
        boolean[][] visite = new boolean[HAUTEUR_GRILLE][LARGEUR_GRILLE];

        int[][] parentLigne = new int[HAUTEUR_GRILLE][LARGEUR_GRILLE];
        int[][] parentColonne = new int[HAUTEUR_GRILLE][LARGEUR_GRILLE];

        for (int ligne = 0; ligne < HAUTEUR_GRILLE; ligne++) {
            for (int colonne = 0; colonne < LARGEUR_GRILLE; colonne++) {
                parentLigne[ligne][colonne] = -1;
                parentColonne[ligne][colonne] = -1;
            }
        }

        ArrayDeque<int[]> file = new ArrayDeque<>();

        file.add(new int[]{ligneDepart, colonneDepart});
        visite[ligneDepart][colonneDepart] = true;

        int[] deplacementLigne = {-1, 1, 0, 0};
        int[] deplacementColonne = {0, 0, -1, 1};

        while (!file.isEmpty()) {
            int[] caseActuelle = file.remove(); //prend les premiere co mis dans la file dans le tableau CaseActuelle

            int ligne = caseActuelle[0];
            int colonne = caseActuelle[1];//Prend les co de la acse actuelle

            if (ligne == ligneArrivee && colonne == colonneArrivee) {
                break;
            } //Verif si on est a l'arrivé

            for (int i = 0; i < 4; i++) {
                int nouvelleLigne = ligne + deplacementLigne[i];
                int nouvelleColonne = colonne + deplacementColonne[i];

                if (estPraticable(nouvelleLigne, nouvelleColonne)
                        && !visite[nouvelleLigne][nouvelleColonne]) {//Verif si c'est un chemin praticable dans le tab logique et si c'est pas deja visité

                    visite[nouvelleLigne][nouvelleColonne] = true;

                    parentLigne[nouvelleLigne][nouvelleColonne] = ligne;
                    parentColonne[nouvelleLigne][nouvelleColonne] = colonne;
                    System.out.println(parentColonne);
                    file.add(new int[]{nouvelleLigne, nouvelleColonne});
                }
            }
        }

        if (!visite[ligneArrivee][colonneArrivee]) {
            return new ArrayList<>();
        }
        List<int[]> chemin = new ArrayList<>();

        int ligne = ligneArrivee;
        int colonne = colonneArrivee;

        while (ligne != ligneDepart || colonne != colonneDepart) {//On ajoute au chemin jusqu'a arriver au depart
            chemin.add(new int[]{ligne, colonne});

            int ancienneLigne = parentLigne[ligne][colonne];
            int ancienneColonne = parentColonne[ligne][colonne];

            ligne = ancienneLigne;
            colonne = ancienneColonne;
        }

        chemin.add(new int[]{ligneDepart, colonneDepart});//On ajoute le départ (la boucle s'arrête avant de l'inclure),
        Collections.reverse(chemin);
        // puis on inverse la liste car on l'a construite à l'envers (de l'arrivée vers le départ).

        return chemin;
    }
}



