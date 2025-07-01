package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Contrôleur souris pour la rotation de la pièce :
 * - clic gauche : horaire
 * - clic droit : antihoraire
 */
public class PieceRotation extends MouseAdapter {

    private final VuePuits vuePuits;

    /**
     * Construit un contrôleur de rotation pour une vue du puits
     *
     * @param vuePuits la vue graphique du puits
     */
    public PieceRotation(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
    }

    /**
     * Gère la rotation de la pièce via clic souris
     *
     * @param e l’événement souris
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Piece piece = vuePuits.getPuits().getPieceActuelle();

        if (piece == null) return;

        try {
            if (e.getButton() == MouseEvent.BUTTON1) {
                piece.tourner(true); // Sens horaire
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                piece.tourner(false); // Sens antihoraire
            }
        } catch (BloxException ex) {
            // Rotation impossible (collision ou hors limites)
        }

        vuePuits.mettreAJourFantome();
        vuePuits.repaint();
    }
}