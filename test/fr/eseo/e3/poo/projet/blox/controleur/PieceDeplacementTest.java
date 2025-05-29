package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import javax.swing.*;

public class PieceDeplacementTest {

    public PieceDeplacementTest() {
        afficherPuitsAvecDeplacement();
    }

    private void afficherPuitsAvecDeplacement() {
        // Création du puits
        Puits puits = new Puits(10, 20);

        // Création de la vue
        VuePuits vuePuits = new VuePuits(puits, VuePuits.TAILLE_PAR_DEFAUT);

        // Activation du mode TEST
        UsineDePiece.setMode(UsineDePiece.Mode.TEST);

        // Génération et positionnement de la pièce
        puits.setPieceActuelle(UsineDePiece.genererPiece());
        puits.getPieceActuelle().setPosition(5, 5); // 🔁 Position plus basse pour éviter les limites
        puits.setPieceSuivante(UsineDePiece.genererPiece());

        // Création de la fenêtre
        JFrame frame = new JFrame("Test Déplacement de Pièce");
        frame.setContentPane(vuePuits);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // 🔁 S'assurer que la vue capte les événements
        vuePuits.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PieceDeplacementTest::new);
    }
}
