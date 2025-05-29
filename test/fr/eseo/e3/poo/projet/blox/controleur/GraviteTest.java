package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.*;

public class GraviteTest {

    public GraviteTest() {
        testerGraviteAutomatique();
    }

    private void testerGraviteAutomatique() {
        // Création du puits
        Puits puits = new Puits(10, 20);

        // Activation du mode test pour générer une pièce déterministe
        UsineDePiece.setMode(UsineDePiece.Mode.TEST);
        puits.setPieceActuelle(UsineDePiece.genererPiece());
        puits.getPieceActuelle().setPosition(5, 5); // Position initiale visible

        // Création de la vue
        VuePuits vuePuits = new VuePuits(puits, VuePuits.TAILLE_PAR_DEFAUT);

        // Création de la fenêtre
        JFrame frame = new JFrame("Test Gravité Automatique");
        frame.setContentPane(vuePuits);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Démarrage de la gravité automatique
        new Gravite(vuePuits, 500); // exécute la gravité toutes les 500 ms

        // Focus pour capter les événements si nécessaire
        vuePuits.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraviteTest::new);
    }
}