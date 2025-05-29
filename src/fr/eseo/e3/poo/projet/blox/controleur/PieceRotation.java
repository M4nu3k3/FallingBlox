package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Gère la rotation des pièces (Tetromino) au clic de souris :
 * - Clic gauche : rotation anti-horaire
 * - Clic droit : rotation horaire
 */
public class PieceRotation extends MouseAdapter {

    private final VuePuits vuePuits;

    public PieceRotation(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tetromino tetromino = null;

        // On ne gère que les pièces de type Tetromino
        if (vuePuits.getPuits().getPieceActuelle() instanceof Tetromino) {
            tetromino = (Tetromino) vuePuits.getPuits().getPieceActuelle();
        }

        // Si la pièce est absente ou déjà figée dans le tas, on ignore l'événement
        if (tetromino == null || vuePuits.getPuits().getTas().contient(tetromino)) {
            return;
        }

        boolean rotationReussie = false;

        try {
            // Clic droit → rotation horaire
            if (SwingUtilities.isRightMouseButton(e)) {
                tetromino.tourner(true);
                if (rotationValide(tetromino)) {
                    rotationReussie = true;
                } else {
                    tetromino.tourner(false); // Annulation
                }
            }
            // Clic gauche → rotation anti-horaire
            else if (SwingUtilities.isLeftMouseButton(e)) {
                tetromino.tourner(false);
                if (rotationValide(tetromino)) {
                    rotationReussie = true;
                } else {
                    tetromino.tourner(true); // Annulation
                }
            }
        } catch (BloxException ex) {
            // Erreur pendant la rotation : on n'effectue aucune action
        }

        if (rotationReussie) {
            vuePuits.repaint();
        }
    }

    /**
     * Vérifie que la rotation ne fait pas sortir la pièce du puits
     * et qu'elle n'entre pas en collision avec le tas.
     */
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