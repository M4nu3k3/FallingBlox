package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.PanneauInformation;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.*;
import java.awt.*;

public class FallingBloxVersion1 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int nbElements = 0;
            int nbLignes = 0;
            Puits puits;

            // Analyse des arguments
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

            // Activation du mode TEST ou ALEATOIRE
            UsineDePiece.setMode(UsineDePiece.Mode.TEST);

            // Vue du puits
            VuePuits vuePuits = new VuePuits(puits);

            // Génération des pièces
            puits.setPieceActuelle(UsineDePiece.genererPiece()); // Position initiale automatique (au sommet)
            puits.setPieceSuivante(UsineDePiece.genererPiece());

            // 🔁 Forcer l'affichage immédiat de la première pièce
            vuePuits.repaint();

            // Gravité (activation explicite)
            new Gravite(vuePuits, 500); // 500 ms entre chaque chute

            // Panneau d'information
            PanneauInformation panneauInfo = new PanneauInformation(puits);

            // Création de la fenêtre
            JFrame frame = new JFrame("Falling Blox");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(vuePuits, BorderLayout.CENTER);
            frame.add(panneauInfo, BorderLayout.EAST);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Focus pour capter les événements
            vuePuits.requestFocus();
        });
    }
}
