package com.example.sae.modele;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PartieTest {

    @Test
    public void argentInitialEstCorrect() {
        // Création d'une partie avec 150 PV et 100 pièces
        Partie partie = new Partie(150, 100);

        // On vérifie que l'argent de départ est bien 100
        assertEquals(100, partie.getArgentJoueur());
    }

    @Test
    public void gagnerArgentFonctionne() {
        // Création d'une partie avec 100 pièces
        Partie partie = new Partie(150, 100);

        // Le joueur gagne 50 pièces
        partie.gagnerArgent(50);

        // 100 + 50 = 150
        assertEquals(150, partie.getArgentJoueur());
    }

    @Test
    public void depenserArgentFonctionne() {
        // Création d'une partie avec 100 pièces
        Partie partie = new Partie(150, 100);

        // Le joueur dépense 40 pièces
        partie.depenserArgent(40);

        // 100 - 40 = 60
        assertEquals(60, partie.getArgentJoueur());
    }

    @Test
    public void depenserArgentNeDescendPasSousZero() {
        // Création d'une partie avec 100 pièces
        Partie partie = new Partie(150, 100);

        // Le joueur essaie de dépenser plus que ce qu'il possède
        partie.depenserArgent(200);

        // L'argent ne doit pas devenir négatif
        assertEquals(0, partie.getArgentJoueur());
    }

    @Test
    public void peutPayerRetourneTrueSiArgentSuffisant() {
        // Création d'une partie avec 100 pièces
        Partie partie = new Partie(150, 100);

        // Le joueur peut payer 80 car il a 100
        assertTrue(partie.peutPayer(80));
    }

    @Test
    public void peutPayerRetourneFalseSiArgentInsuffisant() {
        // Création d'une partie avec 100 pièces
        Partie partie = new Partie(150, 100);

        // Le joueur ne peut pas payer 150 car il a seulement 100
        assertFalse(partie.peutPayer(150));
    }

    @Test
    public void perdrePvFonctionne() {
        // Création d'une partie avec 150 PV
        Partie partie = new Partie(150, 100);

        // Le joueur perd 40 PV
        partie.perdrePv(40);

        // 150 - 40 = 110
        assertEquals(110, partie.getPvJoueur());
    }

    @Test
    public void perdrePvNeDescendPasSousZero() {
        // Création d'une partie avec 150 PV
        Partie partie = new Partie(150, 100);

        // Le joueur perd plus de PV qu'il n'en possède
        partie.perdrePv(200);

        // Les PV ne doivent pas devenir négatifs
        assertEquals(0, partie.getPvJoueur());
    }

    @Test
    public void partiePerdueRetourneTrueQuandPvZero() {
        // Création d'une partie avec 150 PV
        Partie partie = new Partie(150, 100);

        // Le joueur perd tous ses PV
        partie.perdrePv(150);

        // La partie doit être considérée comme perdue
        assertTrue(partie.partiePerdue());
    }
}