package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe contrôleur responsable de l'application de la gravité sur la pièce actuelle
 * à intervalle régulier via un Timer Swing. Le délai s’accélère progressivement dans le temps.
 */
public class Gravite implements ActionListener {

    private final VuePuits vuePuits;
    private final Timer timer;
    private final long tempsDepart;
    private final int delaiInitial;
    private final int delaiMin;
    private int delaiActuel;
    private final int accelerationInterval;
    private final int accelerationStep;

    /**
     * Construit un contrôleur de gravité avec accélération progressive.
     *
     * @param vuePuits  la vue graphique associée au puits
     * @param periodeMs période de départ en millisecondes
     */
    public Gravite(VuePuits vuePuits, int periodeMs) {
        this.vuePuits = vuePuits;
        this.delaiInitial = periodeMs;
        this.delaiActuel = periodeMs;
        this.delaiMin = 100;
        this.accelerationInterval = 10000; // toutes les 10 secondes
        this.accelerationStep = 10;        // réduction de 10 ms à chaque étape
        this.tempsDepart = System.currentTimeMillis();

        this.timer = new Timer(delaiActuel, this);
        this.timer.setInitialDelay(delaiActuel);
        this.timer.start();

        vuePuits.setGravite(this);
    }

    /**
     * Met à jour dynamiquement la période du Timer si le temps écoulé dépasse un palier d'accélération.
     */
    private void mettreAJourDelai() {
        long tempsEcoule = System.currentTimeMillis() - tempsDepart;
        int niveaux = (int) (tempsEcoule / accelerationInterval);
        int nouveauDelai = Math.max(delaiInitial - niveaux * accelerationStep, delaiMin);

        if (nouveauDelai != delaiActuel) {
            delaiActuel = nouveauDelai;
            timer.setDelay(delaiActuel);
        }
    }

    /**
     * Action déclenchée à chaque tick du Timer : fait descendre la pièce actuelle si possible.
     *
     * @param e l'événement déclencheur
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Puits puits = vuePuits.getPuits();

        if (puits.isPartieTerminee()) {
            stop();
            return;
        }

        mettreAJourDelai();

        if (puits.getPieceActuelle() != null) {
            try {
                puits.gravite();
            } catch (BloxException ex) {
                // L’exception est ignorée
            }

            vuePuits.repaint();
        }
    }

    /**
     * Définit une nouvelle période de gravité manuellement.
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
}