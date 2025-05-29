package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PieceRotation extends MouseAdapter {

    private final VuePuits vuePuits;

    public PieceRotation(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tetromino tetromino = null;

        if (vuePuits.getPuits().getPieceActuelle() instanceof Tetromino) {
            tetromino = (Tetromino) vuePuits.getPuits().getPieceActuelle();
        }

        if (tetromino == null || vuePuits.getPuits().getTas().contient(tetromino)) {
            return;
        }

        boolean rotationReussie = false;

        try {
            if (SwingUtilities.isRightMouseButton(e)) {
                tetromino.tourner(true); // sens horaire
                if (rotationValide(tetromino)) {
                    rotationReussie = true;
                } else {
                    tetromino.tourner(false); // annule
                }
            } else if (SwingUtilities.isLeftMouseButton(e)) {
                tetromino.tourner(false); // sens anti-horaire
                if (rotationValide(tetromino)) {
                    rotationReussie = true;
                } else {
                    tetromino.tourner(true); // annule
                }
            }
        } catch (BloxException ex) {
            // erreur de rotation : on ne fait rien
        }

        if (rotationReussie) {
            vuePuits.repaint();
        }
    }

    private boolean rotationValide(Tetromino tetromino) {
        for (Element e : tetromino.getElements()) {
            int x = e.getCoordonnees().getAbscisse();
            int y = e.getCoordonnees().getOrdonnee();
            if (x < 0 || x >= vuePuits.getPuits().getLargeur()
                    || y < 0 || y >= vuePuits.getPuits().getHauteur()
                    || vuePuits.getPuits().getTas().contientCoordonnees(x, y)) {
                return false;
            }
        }
        return true;
    }
}