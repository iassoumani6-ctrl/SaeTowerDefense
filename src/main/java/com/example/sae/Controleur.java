package com.example.sae;

import com.example.sae.modele.Terrain;
import com.example.sae.vue.TerrainVue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    @FXML
    private TilePane tilepane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Terrain terrain = new Terrain();
        TerrainVue terrainVue = new TerrainVue(terrain, tilepane);
        terrainVue.dessinerTerrain();

    }
}
