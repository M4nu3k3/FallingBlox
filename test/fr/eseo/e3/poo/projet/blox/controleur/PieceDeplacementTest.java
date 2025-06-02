package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.*;

/**
 * Classe de test pour vérifier les déplacements d'une pièce à l'aide de la souris.
 */
public class PieceDeplacementTest {

    /**
     * Constructeur qui initialise la fenêtre de test.
     */
    public PieceDeplacementTest() {
        initialiserFenetre();
    }

    /**
     * Crée un puits et une vue graphique avec gestion du déplacement par la souris et contrôle clavier.
     */
    private void initialiserFenetre() {
        // Création du puits
        Puits puits = new Puits(10, 20);

        // Création de la vue du puits
        VuePuits vuePuits = new VuePuits(puits, VuePuits.TAILLE_PAR_DEFAUT);

        // Mode de génération fixé pour avoir des résultats reproductibles
        UsineDePiece.setMode(UsineDePiece.Mode.TEST);

        // Génération et positionnement de la pièce actuelle
        puits.setPieceActuelle(UsineDePiece.genererPiece(puits));
        puits.getPieceActuelle().setPosition(4, 4);

        // Définition de la pièce suivante
        puits.setPieceSuivante(UsineDePiece.genererPiece(puits));

        // Création de la fenêtre Swing
        JFrame frame = new JFrame("Test Déplacement de Pièce");
        frame.setContentPane(vuePuits);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Focus clavier pour capter les événements
        vuePuits.requestFocusInWindow();
    }

    /**
     * Point d'entrée du programme.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PieceDeplacementTest::new);
    }
}