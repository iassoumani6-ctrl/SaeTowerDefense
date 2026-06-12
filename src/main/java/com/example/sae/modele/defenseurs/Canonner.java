package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Tour;
import com.example.sae.modele.Ballon;
import com.example.sae.modele.projectile.Balle_en_feu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class Canonner extends Tour {
    private final List<Balle_en_feu> ballesEnVol = new ArrayList<>();

    public Canonner(int colonne, int ligne) {
        super(colonne, ligne, 130.0, 15, 800);
    }

    @Override
    public boolean tirerSur(Ballon ennemi) {
        long maintenant = System.currentTimeMillis();

        if (maintenant - getDerniereAttaqueMs() < getDelaiAttaqueMs()) {
            return false;
        }

        double cx = getCentrePixelX();
        double cy = getCentrePixelY();
        double ex = ennemi.getX();
        double ey = ennemi.getY();

        double distance = Math.sqrt(Math.pow(ex - cx, 2) + Math.pow(ey - cy, 2));

        if (distance <= getPortee()) {
            // On passe directement le ballon ciblé
            ballesEnVol.add(new Balle_en_feu(cx, cy, ennemi));
            setDerniereAttaqueMs(maintenant);
            return true;
        }

        return false;
    }

    public void update() {
        Iterator<Balle_en_feu> it = ballesEnVol.iterator();
        while (it.hasNext()) {
            Balle_en_feu balle = it.next();
            balle.update(); // le kill est géré dans BalleBasket
            if (balle.isArrivee()) {
                it.remove();
            }
        }
    }

    public List<Balle_en_feu> getBallesEnVol() {
        return ballesEnVol;
    }
    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/canonner.gif";
    }
}