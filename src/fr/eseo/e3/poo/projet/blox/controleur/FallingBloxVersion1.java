package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.PanneauInformation;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.*;
import java.awt.*;

import static fr.eseo.e3.poo.projet.blox.modele.UsineDePiece.Mode.*;

public class FallingBloxVersion1 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            final UsineDePiece.Mode mode = ALEATOIRE;
            UsineDePiece.setMode(mode);

            int nbElements = 0;
            int nbLignes = 0;
            Puits puits;

            if (args.length == 1) {
                nbElements = Integer.parseInt(args[0]);
                puits = new Puits(10, 20, nbElements, (nbElements / 10) + 1);
            } else if (args.length == 2) {
                nbElements = Integer.parseInt(args[0]);
                nbLignes = Integer.parseInt(args[1]);
                puits = new Puits(10, 20, nbElements, nbLignes);
            } else {
                puits = new Puits(10, 20);
            }

            final VuePuits[] vuePuitsRef = new VuePuits[] { new VuePuits(puits) };
            final Gravite[] graviteRef = new Gravite[] { new Gravite(vuePuitsRef[0], 500) };
            vuePuitsRef[0].setGravite(graviteRef[0]);

            final PanneauInformation panneauInfo = new PanneauInformation(puits);
            panneauInfo.setGravite(graviteRef[0]);
            panneauInfo.setVuePuits(vuePuitsRef[0]);

            // Définir les pièces après que tous les écouteurs soient en place
            puits.setPieceActuelle(UsineDePiece.genererPiece());
            puits.setPieceSuivante(UsineDePiece.genererPiece());

            graviteRef[0].start();

            panneauInfo.getBoutonRejouer().addActionListener(e -> {
                graviteRef[0].stop();

                Puits nouveauPuits = new Puits(10, 20);
                VuePuits nouvelleVue = new VuePuits(nouveauPuits);
                Gravite nouvelleGravite = new Gravite(nouvelleVue, graviteRef[0].getPeriode());
                nouvelleVue.setGravite(nouvelleGravite);

                PanneauInformation nouveauPanneau = panneauInfo;
                nouveauPuits.setPieceActuelle(UsineDePiece.genererPiece());
                nouveauPuits.setPieceSuivante(UsineDePiece.genererPiece());

                nouveauPanneau.setPuits(nouveauPuits);
                nouveauPanneau.setGravite(nouvelleGravite);
                nouveauPanneau.setVuePuits(nouvelleVue);

                Container parent = vuePuitsRef[0].getParent();
                parent.remove(vuePuitsRef[0]);
                parent.add(nouvelleVue, BorderLayout.CENTER);
                parent.revalidate();
                parent.repaint();

                vuePuitsRef[0] = nouvelleVue;
                graviteRef[0] = nouvelleGravite;

                graviteRef[0].start();
                vuePuitsRef[0].requestFocus();
            });

            JFrame frame = new JFrame("Falling Blox");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(vuePuitsRef[0], BorderLayout.CENTER);
            frame.add(panneauInfo, BorderLayout.EAST);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            vuePuitsRef[0].requestFocus();
        });
    }
}