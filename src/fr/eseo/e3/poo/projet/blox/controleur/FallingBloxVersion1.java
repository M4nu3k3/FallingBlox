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

            // Définition du mode de génération des pièces (TEST, ALEATOIRE ou CYCLIQUE)
            final UsineDePiece.Mode mode = ALEATOIRE;
            UsineDePiece.setMode(mode);

            // Initialisation des paramètres du puits
            int nbElements = 0;
            int nbLignes = 0;
            Puits puits;

            // Analyse des arguments de la ligne de commande
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

            // Création de la vue du puits
            VuePuits vuePuits = new VuePuits(puits);

            // Initialisation des pièces actuelle et suivante
            puits.setPieceActuelle(UsineDePiece.genererPiece());
            puits.setPieceSuivante(UsineDePiece.genererPiece());

            // Forcer l'affichage initial de la pièce
            vuePuits.repaint();

            // Activation de la gravité (chute automatique toutes les 500 ms)
            new Gravite(vuePuits, 500);

            // Création du panneau d'information
            PanneauInformation panneauInfo = new PanneauInformation(puits);

            // Construction de la fenêtre principale
            JFrame frame = new JFrame("Falling Blox");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(vuePuits, BorderLayout.CENTER);
            frame.add(panneauInfo, BorderLayout.EAST);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Donner le focus à la vue pour capter les entrées clavier
            vuePuits.requestFocus();
        });
    }
}