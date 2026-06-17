package com.example.sae.modele.defenseurs;

import com.example.sae.modele.Ballon;
import com.example.sae.modele.Tour;

import java.util.List;

/**
 * Le Laser : tour de moyenne portée qui verrouille un ballon dès qu'il
 * entre dans sa zone d'action. Tant que la cible est vivante et reste à
 * portée, elle subit des dégâts en continu. Le délai très court fait que
 * les dégâts s'apparentent à un faisceau continu.
 */
public class Laser extends Tour {

    private Ballon cibleVerrouillee;

    public Laser(int colonne, int ligne) {
        //          portée  dégâts  délai(ms)
        super(colonne, ligne, 160.0, 6, 150);
    }

    @Override
    public boolean attaquer(List<Ballon> ennemis) {
        long maintenant = System.currentTimeMillis();

        // La cible verrouillée est-elle toujours valide ?
        if (cibleVerrouillee != null
                && (cibleVerrouillee.estMort()
                    || !estEnPortee(cibleVerrouillee)
                    || !ennemis.contains(cibleVerrouillee))) {
            cibleVerrouillee = null;
        }

        // Sinon on verrouille un nouveau ballon entré dans la zone.
        if (cibleVerrouillee == null) {
            cibleVerrouillee = choisirCible(ennemis);
        }

        if (cibleVerrouillee == null) {
            return false;
        }

        // Dégâts continus, cadencés par le délai (très court).
        if (!pretAAttaquer(maintenant)) {
            return false;
        }

        cibleVerrouillee.subirDegats(getDegatsParTir());
        marquerAttaque(maintenant);
        return true;
    }

    public Ballon getCibleVerrouillee() {
        return cibleVerrouillee;
    }

    @Override
    public String getCheminImage() {
        return "/com/example/sae/image/LaserAnim/Laser.png";
    }
}
