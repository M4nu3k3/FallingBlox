package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe contrôleur responsable de l'application de la gravité sur la pièce actuelle
 * à intervalle régulier via un Timer Swing.
 */
public class Gravite implements ActionListener {

    /** Référence à la vue du puits pour obtenir le modèle et forcer le repaint. */
    private final VuePuits vuePuits;

    /** Timer interne déclenchant l'action de gravité périodiquement. */
    private final Timer timer;

    /**
     * Construit un contrôleur de gravité.
     *
     * @param vuePuits  la vue graphique associée au puits
     * @param periodeMs période entre chaque application de la gravité, en millisecondes
     */
    public Gravite(VuePuits vuePuits, int periodeMs) {
        this.vuePuits = vuePuits;
        this.timer = new Timer(periodeMs, this);
        this.timer.setInitialDelay(periodeMs); // Délai initial pour éviter un déplacement immédiat
        this.timer.start();
    }

    /**
     * Définit une nouvelle période de gravité.
     *
     * @param periodeMs la nouvelle période en millisecondes
     */
    public void setPeriode(int periodeMs) {
        this.timer.setDelay(periodeMs);
    }

    /**
     * Retourne la période actuelle de gravité.
     *
     * @return période en millisecondes
     */
    public int getPeriode() {
        return this.timer.getDelay();
    }

    /**
     * Arrête le Timer de gravité.
     */
    public void stop() {
        this.timer.stop();
    }

    /**
     * Redémarre le Timer de gravité si ce n’est pas déjà le cas.
     */
    public void start() {
        if (!this.timer.isRunning()) {
            this.timer.restart();
        }
    }

    /**
     * Indique si le Timer de gravité est en cours d'exécution.
     *
     * @return true si le Timer tourne, false sinon
     */
    public boolean isRunning() {
        return this.timer.isRunning();
    }

    /**
     * Action déclenchée à chaque tick du Timer : fait descendre la pièce actuelle si possible.
     *
     * @param e l'événement déclencheur
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Puits puits = vuePuits.getPuits();

        // Si la partie est finie, on arrête la gravité
        if (puits.isPartieTerminee()) {
            stop();
            return;
        }

        // Applique la gravité sur la pièce courante si elle existe
        if (puits.getPieceActuelle() != null) {
            try {
                puits.gravite();
            } catch (BloxException ex) {
                // Fin de chute de la pièce ou mouvement impossible : on ignore l’exception
            }

            // Rafraîchit l’affichage
            vuePuits.repaint();
        }
    }
}