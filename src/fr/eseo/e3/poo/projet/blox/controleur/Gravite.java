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
        this.timer.start();
    }

    public void setPeriode(int periodeMs) {
        this.timer.setDelay(periodeMs);
    }

    public int getPeriode() {
        return this.timer.getDelay();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Puits puits = vuePuits.getPuits();
        if (puits.getPieceActuelle() != null) {
            try {
                puits.gravite();
            } catch (BloxException ex) {
                // La gravité s’arrête uniquement quand une exception est levée (collision ou sol)
                // -> gérerCollision déjà appelée dans gravite() si besoin
            }
            vuePuits.repaint();
        }
    }
}
