package com.example.sae.modele;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TerrainTest {

    @Test
    public void caseCheminEstPraticable() {
        // On crée un nouveau terrain
        Terrain terrain = new Terrain();

        // On vérifie qu'une case du chemin est praticable
        // Ici (5,0) correspond à une case où les ennemis peuvent passer
        assertTrue(terrain.estPraticable(5, 0));
    }

    @Test
    public void caseBloqueeNestPasPraticable() {
        // Création du terrain
        Terrain terrain = new Terrain();

        // On vérifie qu'une case bloquée n'est PAS praticable
        // Ici (0,0) est hors du chemin
        assertFalse(terrain.estPraticable(0, 0));
    }

    @Test
    public void caseHorsGrilleNestPasPraticable() {
        // Création du terrain
        Terrain terrain = new Terrain();

        // Test d'une case négative → invalide
        assertFalse(terrain.estPraticable(-1, 0));

        // Test d'une case beaucoup trop loin → invalide
        assertFalse(terrain.estPraticable(100, 1000));
    }

    @Test
    public void peutPlacerTourSurCaseLibre() {
        // Création du terrain
        Terrain terrain = new Terrain();

        // On vérifie qu'on peut poser une tour sur une case libre
        // Ici la case (1,1) est supposée vide
        assertTrue(terrain.peutPlacerTour(1, 1, Tour.TAILLE_CASES));
    }

    @Test
    public void nePeutPasPlacerTourSurChemin() {
        // Création du terrain
        Terrain terrain = new Terrain();

        // On vérifie qu'on ne peut PAS poser une tour sur le chemin
        // Sinon ça bloquerait les ennemis
        assertFalse(terrain.peutPlacerTour(5, 0, Tour.TAILLE_CASES));
    }
}