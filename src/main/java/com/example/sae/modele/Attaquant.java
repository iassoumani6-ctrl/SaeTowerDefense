package com.example.sae.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Attaquant {

    private double x;
    private double y;
    private int indicePoint;
    private double pixDeplacement;

    private final double[][] chemin = {
            {0, 96}, //point 1
            {320, 96},//point 2
            {320, 224},//point 3
            {64, 224},//point 4
            {64, 352},//point 5
            {416, 352},
            {416, 96},
            {736, 96},
            {736, 352},
            {992, 352},
            {992, 512},
            {256, 512},
            {256, 704}
    };

    public Attaquant() {
        this.x = chemin[0][0]; // point 1 valeur x
        this.y = chemin[0][1]; // point 1 valeur y
        this.pixDeplacement = 1;
        this.indicePoint = 0;
    }

    public void avancer() {
        if (indicePoint >= chemin.length - 1) {
            return;
        } //si le ballon arrive au dernier point il ne bouge plus

        double cibleX = chemin[indicePoint + 1][0];//Recup du prochain point a atteindre
        double cibleY = chemin[indicePoint + 1][1];

        double dx = cibleX - x;//calcul distance entre ballon et point
        double dy = cibleY - y;

        double distance = Math.sqrt(dx * dx + dy * dy);//formule de distance entre 2point

        if (distance <= pixDeplacement) {
            x = cibleX;
            y = cibleY;
            indicePoint++;

        } else {

            x += pixDeplacement * dx / distance; //tan tque le ballon n'est pas arrivé a sa cible il avance
            y += pixDeplacement * dy / distance;// un peu a la vitesse de pixDeplacement
        }
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}