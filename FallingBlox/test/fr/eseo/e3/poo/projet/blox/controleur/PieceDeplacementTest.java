package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.*;

/**
 * Classe de test permettant d’afficher un puits avec une pièce
 * et de tester les déplacements via les événements souris.
 */
public class PieceDeplacementTest {

    // Constructeur déclenchant automatiquement l'affichage
    public PieceDeplacementTest() {
        afficherPuitsAvecDeplacement();
    }

    // Initialise le puits, insère une pièce et prépare la vue interactive
    private void afficherPuitsAvecDeplacement() {
        // Création du puits
        Puits puits = new Puits(10, 20);

        // Création de la vue graphique du puits
        VuePuits vuePuits = new VuePuits(puits, VuePuits.TAILLE_PAR_DEFAUT);

        // Activation du mode TEST pour générer des pièces déterministes
        UsineDePiece.setMode(UsineDePiece.Mode.TEST);

        // Génération de la pièce actuelle et positionnement visible
        puits.setPieceActuelle(UsineDePiece.genererPiece());
        puits.getPieceActuelle().setPosition(5, 5);

        // Génération d’une pièce suivante (utile pour test de transition)
        puits.setPieceSuivante(UsineDePiece.genererPiece());

        // Création et configuration de la fenêtre Swing
        JFrame frame = new JFrame("Test Déplacement de Pièce");
        frame.setContentPane(vuePuits);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrage de la fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Permet à la vue de capter les événements souris et clavier
        vuePuits.requestFocus();
    }

    // Point d'entrée du programme
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PieceDeplacementTest::new);
    }
}