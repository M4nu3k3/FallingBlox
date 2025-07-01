package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Contrôleur clavier
 * Gère les interactions clavier pour déplacer, faire tourner ou faire descendre rapidement la pièce actuelle.
 */
public class Clavier extends KeyAdapter {

    private final Puits puits;
    private final VuePuits vuePuits;

    /**
     * Construit un contrôleur clavier associé à un puits et sa vue.
     *
     * @param puits     le modèle logique du jeu
     * @param vuePuits  la vue graphique du puits
     */
    public Clavier(Puits puits, VuePuits vuePuits) {
        this.puits = puits;
        this.vuePuits = vuePuits;
    }

    /**
     * Gère les appuis sur les touches directionnelles et Shift :
     * ← : déplacement gauche
     * → : déplacement droite
     * ↑ : rotation horaire
     * ↓ : rotation antihoraire
     * Shift : descente rapide
     *
     * @param e l’événement clavier déclenché
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (puits.isPartieTerminee()) return;

        Piece piece = puits.getPieceActuelle();
        if (piece == null) return;

        int code = e.getKeyCode();

        try {
            switch (code) {
                case KeyEvent.VK_LEFT:
                    piece.deplacerDe(-1, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    piece.deplacerDe(1, 0);
                    break;
                case KeyEvent.VK_UP:
                    piece.tourner(true);
                    break;
                case KeyEvent.VK_DOWN:
                    piece.tourner(false);
                    break;
                case KeyEvent.VK_SHIFT:
                    puits.gravite();
                    break;
                default:
                    return;
            }
        } catch (BloxException ex) {
            // Mouvement non autorisé : on ignore
        }

        // Synchronise la pièce fantôme après tout mouvement
        vuePuits.mettreAJourFantome();
        vuePuits.repaint();
    }
}