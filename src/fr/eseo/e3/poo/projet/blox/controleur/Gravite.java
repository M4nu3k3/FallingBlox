package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Gère la chute automatique des pièces dans le puits,
 * déclenchée à intervalles réguliers par un Timer Swing.
 */
public class Gravite implements ActionListener {

    private final VuePuits vuePuits;
    private final Timer timer;

    /**
     * Crée une instance de gravité avec une période spécifiée.
     *
     * @param vuePuits  la vue associée au puits
     * @param periodeMs délai entre chaque chute automatique en millisecondes
     */
    public Gravite(VuePuits vuePuits, int periodeMs) {
        this.vuePuits = vuePuits;
        this.timer = new Timer(periodeMs, this);
        this.timer.start();
    }

    /**
     * Modifie la période de chute.
     *
     * @param periodeMs nouvelle période en millisecondes
     */
    public void setPeriode(int periodeMs) {
        this.timer.setDelay(periodeMs);
    }

    /**
     * Retourne la période actuelle.
     *
     * @return délai actuel en millisecondes
     */
    public int getPeriode() {
        return this.timer.getDelay();
    }

    /**
     * Méthode appelée à chaque tick du Timer :
     * si une pièce est active, on applique la gravité,
     * puis on rafraîchit la vue.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Puits puits = vuePuits.getPuits();
        if (puits.getPieceActuelle() != null) {
            try {
                puits.gravite();
            } catch (BloxException ex) {
                // Exception attendue : la gravité est arrêtée automatiquement par le modèle
            }
            vuePuits.repaint();
        }
    }
}