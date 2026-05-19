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
//        this.tilePane.setPrefColumns(terrain.largeur());
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

        for (int ligne = 0; ligne < terrain.getHauteur(); ligne++) {
            for (int col = 0; col < terrain.getLargeur(); col++) {
                switch (terrain.getCodeTuile(ligne, col)) {
                    case 0:
                        tilePane.getChildren().add(new ImageView(beige));
                        break;
                    case 1:
                        tilePane.getChildren().add(new ImageView(bleu));
                        break;
                    case 2:
                        tilePane.getChildren().add(new ImageView(ligne1));
                        break;
                    case 3:
                        tilePane.getChildren().add(new ImageView(ligne2));
                        break;
                    case 4:
                        tilePane.getChildren().add(new ImageView(cornerA1));
                        break;
                    case 5:
                        tilePane.getChildren().add(new ImageView(cornerA2));
                        break;
                    case 6:
                        tilePane.getChildren().add(new ImageView(cornerA3));
                        break;
                    case 7:
                        tilePane.getChildren().add(new ImageView(cornerA4));
                        break;
                    case 8:
                        tilePane.getChildren().add(new ImageView(cornerB1));
                        break;
                    case 9:
                        tilePane.getChildren().add(new ImageView(cornerB2));
                        break;
                    case 10:
                        tilePane.getChildren().add(new ImageView(cornerB3));
                        break;
                    case 11:
                        tilePane.getChildren().add(new ImageView(cornerB4));
                        break;
                    case 12:
                        tilePane.getChildren().add(new ImageView(tribune1));
                        break;
                    case 13:
                        tilePane.getChildren().add(new ImageView(tribune2));
                        break;
                    case 14:
                        tilePane.getChildren().add(new ImageView(tribune3));
                        break;
                    case 15:
                        tilePane.getChildren().add(new ImageView(tribune4));
                        break;
                    case 16:
                        tilePane.getChildren().add(new ImageView(tribune5));
                        break;
                    case 17:
                        tilePane.getChildren().add(new ImageView(ligne3));
                        break;
                    case 18:
                        tilePane.getChildren().add(new ImageView(ligne4));
                        break;
                    default:
                        tilePane.getChildren().add(new ImageView(beige));
                        break;
                }
            }
        }
    }

}
