package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

/**
 * Contrôleur souris pour gérer les déplacements horizontaux
 * et verticaux de la pièce dans le puits :
 * Déplacement horizontal par mouvement de souris
 * Descente par molette de souris
 */
public class PieceDeplacement implements MouseMotionListener, MouseListener, MouseWheelListener {

    private final Puits puits;
    private final VuePuits vuePuits;
    private Integer derniereColonne = null;

    /**
     * Construit un contrôleur de déplacement souris pour une vue du puits.
     *
     * @param puits     le modèle du puits
     * @param vuePuits  la vue graphique du puits
     */
    public PieceDeplacement(Puits puits, VuePuits vuePuits) {
        this.puits = puits;
        this.vuePuits = vuePuits;
    }

    /**
     * Gère les déplacements horizontaux de la pièce en fonction
     * de la position actuelle de la souris sur l’écran.
     *
     * @param e l’événement de déplacement de souris
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (puits.getPieceActuelle() == null) return;

        int taille = vuePuits.getTaille();
        int colonne = e.getX() / taille;

        if (derniereColonne == null) {
            derniereColonne = colonne;
            return;
        }

        if (colonne != derniereColonne) {
            int colonneActuelle = puits.getPieceActuelle()
                    .getElements().get(1).getCoordonnees().getAbscisse();

            int delta = colonne - colonneActuelle;

            // On limite le déplacement à une colonne par mouvement
            if (delta < -1) delta = -1;
            if (delta > 1) delta = 1;

            try {
                puits.getPieceActuelle().deplacerDe(delta, 0);
                vuePuits.repaint();
            } catch (BloxException | IllegalArgumentException ex) {
                // Déplacement non possible : on ignore
            }

            derniereColonne = colonne;
        }
    }

    /**
     * Gère le déplacement vers le bas de la pièce
     * lorsqu’on utilise la molette de la souris.
     *
     * @param e l’événement molette
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (puits.getPieceActuelle() != null && e.getWheelRotation() > 0) {
            try {
                puits.getPieceActuelle().deplacerDe(0, 1);
                vuePuits.repaint();
            } catch (BloxException ex) {
                // Collision ou bas du puits : on ignore
            }
        }
    }

    // Méthodes inutilisées des interfaces implémentées
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
}