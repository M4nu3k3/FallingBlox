package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.*;

public class PieceRotationTest {

    public PieceRotationTest() {
        afficherPuitsAvecRotation();
    }

    private void afficherPuitsAvecRotation() {
        // Création du puits
        Puits puits = new Puits(10, 20);

        // Création de la vue (doit être fait avant de définir les pièces)
        VuePuits vuePuits = new VuePuits(puits, VuePuits.TAILLE_PAR_DEFAUT);

        // Activation du mode TEST pour garantir un ITetromino
        UsineDePiece.setMode(UsineDePiece.Mode.TEST);

        // Définir les pièces après que VuePuits soit connectée
        puits.setPieceActuelle(UsineDePiece.genererPiece());
        puits.getPieceActuelle().setPosition(5, 5); // 🔁 repositionne la pièce plus bas
        puits.setPieceSuivante(UsineDePiece.genererPiece());

        // Création de la fenêtre
        JFrame frame = new JFrame("Test Rotation de Pièce");
        frame.setContentPane(vuePuits);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // 🔁 S'assurer que la vue capte les événements
        vuePuits.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PieceRotationTest::new);
    }
}