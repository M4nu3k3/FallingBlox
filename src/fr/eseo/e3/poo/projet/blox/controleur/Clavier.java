package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Clavier implements KeyListener {

    private final Puits puits;
    private final VuePuits vuePuits;

    public Clavier(Puits puits, VuePuits vuePuits) {
        this.puits = puits;
        this.vuePuits = vuePuits;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (puits.getPieceActuelle() == null) return;

        try {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    puits.getPieceActuelle().deplacerDe(-1, 0);
                    break;

                case KeyEvent.VK_RIGHT:
                    puits.getPieceActuelle().deplacerDe(1, 0);
                    break;

                case KeyEvent.VK_UP:
                    puits.getPieceActuelle().tourner(true); // rotation dans un sens
                    break;

                case KeyEvent.VK_DOWN:
                    puits.getPieceActuelle().tourner(false); // rotation dans l'autre sens
                    break;

                case KeyEvent.VK_SHIFT:
                    // chute rapide : faire descendre la pièce jusqu'à collision
                    while (true) {
                        try {
                            puits.getPieceActuelle().deplacerDe(0, 1);
                        } catch (BloxException ex) {
                            break; // arrêt quand la pièce ne peut plus descendre
                        }
                    }
                    break;
            }
        } catch (BloxException | IllegalArgumentException ex) {
            // déplacement ou rotation invalide → ignorer
        }

        vuePuits.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}