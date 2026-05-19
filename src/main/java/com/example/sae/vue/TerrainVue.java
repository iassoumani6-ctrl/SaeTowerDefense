package com.example.sae.vue;

import com.example.sae.Main;
import com.example.sae.modele.Terrain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class TerrainVue {

    private Terrain terrain;
    private TilePane tilePane;

    public TerrainVue(Terrain terrain, TilePane tilePane) {
        this.terrain = terrain;
        this.tilePane = tilePane;

        int tailleTuile = 32;

        this.tilePane.setPrefColumns(terrain.getLargeur());// Les 2 lignes ici indique au TilePane combien de colonnes et lignes il doit afficher
        this.tilePane.setPrefRows(terrain.getHauteur());//selon la hauteur et largeur du tableau

        this.tilePane.setPrefWidth(terrain.getLargeur() * tailleTuile);// Largeur/hauteur totale de la map : nombre de colonnes/lignes × 32
        this.tilePane.setPrefHeight(terrain.getHauteur() * tailleTuile);

        this.tilePane.setMinWidth(terrain.getLargeur() * tailleTuile);// Les 2 lignes font en sorte que le TilePane garde la meme taille
        this.tilePane.setMinHeight(terrain.getHauteur() * tailleTuile);//meme si la fenetre est reduite (enlever le pour tester (Samy))

        this.tilePane.setMaxWidth(terrain.getLargeur() * tailleTuile);// Pareille que les 2lignes precedente mais cette fois pour faire en
        this.tilePane.setMaxHeight(terrain.getHauteur() * tailleTuile);// sorte que le TilePane garde la meme taille si la fenetre est agrandi
    }

    public void dessinerTerrain() {
        Image beige = new Image(Main.class.getResourceAsStream("/com/example/sae/image/beige.png"));
        Image bleu = new Image(Main.class.getResourceAsStream("/com/example/sae/image/bleu.png"));

        Image cornerA1 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageChemin/CornerA1.png"));
        Image cornerA2 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageChemin/CornerA2.png"));
        Image cornerA3 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageChemin/CornerA3.png"));
        Image cornerA4 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageChemin/CornerA4.png"));

        Image cornerB1 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageChemin/CornerB1.png"));
        Image cornerB2 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageChemin/CornerB2.png"));
        Image cornerB3 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageChemin/CornerB3.png"));
        Image cornerB4 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageChemin/CornerB4.png"));

        Image ligne1 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageChemin/Ligne1.png"));
        Image ligne2 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageChemin/Ligne2.png"));
        Image ligne3 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageChemin/Ligne3.png"));
        Image ligne4 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageChemin/Ligne4.png"));

        Image tribune1 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageTribune/tribune_1.png"));
        Image tribune2 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageTribune/tribune_2.png"));
        Image tribune3 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageTribune/tribune_3.png"));
        Image tribune4 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageTribune/tribune_4.png"));
        Image tribune5 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageTribune/tribune_5.png"));

        Image tribune6 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageTribune/tribune_6.png"));
        Image tribune7 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageTribune/tribune_7.png"));
        Image tribune8 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageTribune/tribune_8.png"));
        Image tribune9 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageTribune/tribune_9.png"));
        Image tribune10 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/imageTribune/tribune_10.png"));

        Image LogoMid1 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/LogoMid1.png"));
        Image LogoMid2 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/LogoMid2.png"));
        Image LogoMid3 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/LogoMid3.png"));
        Image LogoMid4 = new Image(Main.class.getResourceAsStream("/com/example/sae/image/LogoMid4.png"));


        for (int ligne = 0; ligne < terrain.getHauteur(); ligne++) {
            for (int col = 0; col < terrain.getLargeur(); col++) {

                Image imageTuile;

                switch (terrain.getCodeTuile(ligne, col)) {
                    case 0:
                        imageTuile = beige;
                        break;
                    case 1:
                        imageTuile = bleu;
                        break;
                    case 2:
                        imageTuile = ligne1;
                        break;
                    case 3:
                        imageTuile = ligne2;
                        break;
                    case 4:
                        imageTuile = cornerA1;
                        break;
                    case 5:
                        imageTuile = cornerA2;
                        break;
                    case 6:
                        imageTuile = cornerA3;
                        break;
                    case 7:
                        imageTuile = cornerA4;
                        break;
                    case 8:
                        imageTuile = cornerB1;
                        break;
                    case 9:
                        imageTuile = cornerB2;
                        break;
                    case 10:
                        imageTuile = cornerB3;
                        break;
                    case 11:
                        imageTuile = cornerB4;
                        break;
                    case 12:
                        imageTuile = tribune1;
                        break;
                    case 13:
                        imageTuile = ligne3;
                        break;
                    case 14:
                        imageTuile = ligne4;
                        break;
                    case 15:
                        imageTuile = tribune2;
                        break;
                    case 16:
                        imageTuile = tribune3;
                        break;
                    case 17:
                        imageTuile = tribune4;
                        break;
                    case 18:
                        imageTuile = tribune5;
                        break;
                    case 19:
                        imageTuile = ligne3;
                        break;
                    case 20:
                        imageTuile = ligne4;
                        break;
                    case 21:
                        imageTuile = tribune6;
                        break;
                    case 22:
                        imageTuile = tribune7;
                        break;
                    case 23:
                        imageTuile = tribune8;
                        break;
                    case 24:
                        imageTuile = tribune9;
                        break;
                    case 25:
                        imageTuile = tribune10;
                        break;
                    case 26:
                        imageTuile = LogoMid1;
                        break;
                    case 27:
                        imageTuile = LogoMid2;
                        break;
                    case 28:
                        imageTuile = LogoMid3;
                        break;
                    case 29:
                        imageTuile = LogoMid4;
                        break;
                    default:
                        imageTuile = beige;
                        break;
                }


                ImageView imageView = new ImageView(imageTuile);
                imageView.setFitWidth(32);
                imageView.setFitHeight(32);
                tilePane.getChildren().add(imageView);
            }
        }
    }

}
