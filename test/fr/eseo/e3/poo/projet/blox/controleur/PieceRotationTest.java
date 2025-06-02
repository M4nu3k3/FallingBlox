package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.*;

/**
 * Classe de test pour afficher un puits et tester la rotation des pièces via souris et clavier
 */
public class PieceRotationTest {

    /**
     * Constructeur du test : initialise et affiche la scène de test
     */
    public PieceRotationTest() {
        afficherPuitsAvecRotation();
    }

    /**
     * Prépare l'affichage du puits et ajoute les pièces nécessaires pour la rotation
     */
    private void afficherPuitsAvecRotation() {
        // Initialisation du puits avec dimensions standards
        Puits puits = new Puits(10, 20);

        // Préparation de la vue associée
        VuePuits vuePuits = new VuePuits(puits, VuePuits.TAILLE_PAR_DEFAUT);

        // Mode TEST pour forcer une pièce connue (ITetromino)
        UsineDePiece.setMode(UsineDePiece.Mode.TEST);

        // Insertion de la pièce actuelle
        puits.setPieceActuelle(UsineDePiece.genererPiece(puits));
        puits.getPieceActuelle().setPosition(4, 4);

        // Insertion d'une pièce suivante pour test visuel dans le panneau
        puits.setPieceSuivante(UsineDePiece.genererPiece(puits));

        // Création de la fenêtre Swing
        JFrame frame = new JFrame("Test Rotation de Pièce");
        frame.setContentPane(vuePuits);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Donne le focus clavier à la vue
        vuePuits.requestFocusInWindow();
    }

    /**
     * Test
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PieceRotationTest::new);
    }
}