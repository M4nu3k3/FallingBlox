package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

public class PieceDeplacement implements MouseMotionListener, MouseListener, MouseWheelListener {

    private final Puits puits;
    private final VuePuits vuePuits;
    private Integer derniereColonne = null;

    public PieceDeplacement(Puits puits, VuePuits vuePuits) {
        this.puits = puits;
        this.vuePuits = vuePuits;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (puits.getPieceActuelle() == null) {
            return;
        }

        int taille = vuePuits.getTaille();
        int colonne = e.getX() / taille;

        if (derniereColonne == null) {
            derniereColonne = colonne;
            return;
        }

        if (colonne != derniereColonne) {
            int colonneActuelle = puits.getPieceActuelle().getElements().get(1).getCoordonnees().getAbscisse();
            int delta = colonne - colonneActuelle;

            // 🔒 Ne jamais permettre un delta > 1 ou < -1
            if (delta < -1) delta = -1;
            if (delta > 1) delta = 1;

            try {
                puits.getPieceActuelle().deplacerDe(delta, 0);
                vuePuits.repaint();
            } catch (BloxException | IllegalArgumentException ex) {
                // déplacement invalide → ignorer
            }

            derniereColonne = colonne;
        }
    }


    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (puits.getPieceActuelle() != null && e.getWheelRotation() > 0) {
            try {
                puits.getPieceActuelle().deplacerDe(0, 1);
                vuePuits.repaint();
            } catch (BloxException ex) {
                // collision
            }
        }
    }

    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
}
