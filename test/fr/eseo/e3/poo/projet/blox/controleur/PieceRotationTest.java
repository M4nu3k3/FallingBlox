package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.*;

/**
 * Classe de test pour afficher un puits et tester la rotation
 * des pièces via les événements de la souris (clic gauche/droit).
 */
public class PieceRotationTest {

    // Constructeur : lance automatiquement l'affichage du test
    public PieceRotationTest() {
        afficherPuitsAvecRotation();
    }

    // Prépare l'affichage du puits et insère les pièces nécessaires
    private void afficherPuitsAvecRotation() {
        // Initialisation du puits avec taille standard
        Puits puits = new Puits(10, 20);

        // Création de la vue graphique associée au puits
        VuePuits vuePuits = new VuePuits(puits, VuePuits.TAILLE_PAR_DEFAUT);

        // Mode TEST pour forcer la génération d’un ITetromino
        UsineDePiece.setMode(UsineDePiece.Mode.TEST);

        // Insertion de la pièce actuelle avec position visible
        puits.setPieceActuelle(UsineDePiece.genererPiece());
        puits.getPieceActuelle().setPosition(5, 5);

        // Définition de la pièce suivante (utile pour tests visuels)
        puits.setPieceSuivante(UsineDePiece.genererPiece());

        // Création de la fenêtre d’affichage Swing
        JFrame frame = new JFrame("Test Rotation de Pièce");
        frame.setContentPane(vuePuits);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrage
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Mise au focus de la vue pour capter les événements souris
        vuePuits.requestFocus();
    }

    // Point d’entrée de l’application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PieceRotationTest::new);
    }
}