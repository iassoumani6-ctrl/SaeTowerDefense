package com.example.sae.modele;

import com.example.sae.modele.ennemis.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère les vagues d'ennemis : composition, intervalle de spawn, progression.
 */
public class GestionnaireVagues {

    private static final int INTERVALLE_SPAWN_TICKS = 25;   // 50ms entre chaque ennemi

    // délai en ticks entre la fin d'une vague et le début de la suivante
    private static final int DELAI_INTER_VAGUE_TICKS = 50;  // 50ms

    private int numeroVague = 0;        // vague actuelle
    private List<Ballon> fileSpawn;     // ennemis encore à spawner dans la vague courante
    private int ticksDepuisDernierSpawn = 0;
    private boolean vagueEnCours = false;
    private int ticksAttenteInterVague = 0;
    private boolean partieFinie = false;
    private int vagueSup6Multiplicateur = 1;

    private static final int NB_VAGUES_MAX = 5;
    // -1 = infini

    public GestionnaireVagues() {
        this.fileSpawn = new ArrayList<>();
    }

    public Ballon tick() {
        if (partieFinie) return null;

        if (!vagueEnCours) {
            this.ticksAttenteInterVague++;
            if (this.ticksAttenteInterVague >= DELAI_INTER_VAGUE_TICKS) {
                demarrerVagueSuivante();
                this.ticksAttenteInterVague = 0;
            }
            return null;
        }
        if (this.fileSpawn.isEmpty()) {
            return null;
        }

        this.ticksDepuisDernierSpawn++;
        if (this.ticksDepuisDernierSpawn >= INTERVALLE_SPAWN_TICKS) {
            this.ticksDepuisDernierSpawn = 0;
            return fileSpawn.remove(0);
        }

        return null;
    }

    public void signalerVagueFinie() {
        this.vagueEnCours = false;
        this.ticksAttenteInterVague = 0;
        this.ticksDepuisDernierSpawn = 0;
    }

    public boolean isVagueEnCours() {
        return this.vagueEnCours;
    }

    public boolean isFileSpawnVide() {
        return this.fileSpawn.isEmpty();
    }

    public int getNumeroVague() {
        return this.numeroVague;
    }

    public boolean isPartieFinie() {
        return this.partieFinie;
    }

    private void demarrerVagueSuivante() {
        if (NB_VAGUES_MAX > 0 && numeroVague >= NB_VAGUES_MAX) {
            this.partieFinie = true;
            return;
        }

        if(this.getNumeroVague() >= 6) {
            vagueSup6Multiplicateur++;
        }
        numeroVague++;
        this.fileSpawn = construireVague(numeroVague);
        this.vagueEnCours = true;
    }

    private List<Ballon> construireVague(int numero) {
        List<Ballon> liste = new ArrayList<>();

        switch (numero) {
            case 1:
                for (int i = 0; i < 5; i++) liste.add(new BallonVert());
                break;

            case 2:

                for (int i = 0; i < 5; i++) liste.add(new BallonVert());
                for (int i = 0; i < 3; i++) liste.add(new BallonJaune());
                break;

            case 3:

                for (int i = 0; i < 4; i++) liste.add(new BallonJaune());
                for (int i = 0; i < 4; i++) liste.add(new BallonOrange());
                for (int i = 0; i <4; i++) liste.add(new BallonOrange());
                break;

            case 4:

                for (int i = 0; i < 6; i++) liste.add(new BallonOrange());
                for (int i = 0; i < 2; i++) liste.add(new BallonRouge());
                for(int i = 0; i< 5; i++) liste.add(new BallonRouge());
                break;

            case 5:
                for (int i = 0; i < 7; i++) liste.add(new BallonRouge());
                liste.add(new MegaBallon());
                break;

            default:
                int base = 4 + (vagueSup6Multiplicateur*2);
                // génération aléatoires
                for (int i = 0; i < base / 2 ; i++) liste.add(new BallonRouge());
                for (int i = 0; i < base /2; i++) liste.add(new BallonOrange());

                // 1 boss tous les 3 niveaux de vagues
                for (int i = 0; i < numero /2; i++) liste.add(new MegaBallon());
                break;
        }

        return liste;
    }
}