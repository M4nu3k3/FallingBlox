package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gravite implements ActionListener {

    private final VuePuits vuePuits;
    private final Timer timer;

    public Gravite(VuePuits vuePuits, int periodeMs) {
        this.vuePuits = vuePuits;
        this.timer = new Timer(periodeMs, this);
        this.timer.setInitialDelay(periodeMs); // pour éviter le saut immédiat au début
        this.timer.start();
    }

    public void setPeriode(int periodeMs) {
        this.timer.setDelay(periodeMs);
    }

    public int getPeriode() {
        return this.timer.getDelay();
    }

    public void stop() {
        this.timer.stop();
    }

    public void start() {
        if (!this.timer.isRunning()) {
            this.timer.restart(); // utilise restart pour redémarrer proprement avec délai
        }
    }

    public boolean isRunning() {
        return this.timer.isRunning();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Puits puits = vuePuits.getPuits();

        if (puits.isPartieTerminee()) {
            stop();
            return;
        }

        if (puits.getPieceActuelle() != null) {
            try {
                puits.gravite();
            } catch (BloxException ex) {
                // Fin de chute de la pièce actuelle
            }
            vuePuits.repaint();
        }
    }
}