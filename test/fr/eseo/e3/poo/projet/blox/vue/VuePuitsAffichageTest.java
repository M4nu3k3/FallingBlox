package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;

import javax.swing.*;

public class VuePuitsAffichageTest {

    public VuePuitsAffichageTest() {
        testConstructeurPuits();
        testConstructeurPuitsTaille();
    }

    private void testConstructeurPuits() {
        Puits puits = new Puits(10, 20);
        VuePuits vuePuits = new VuePuits(puits);

        UsineDePiece.setMode(UsineDePiece.Mode.TEST);
        puits.setPieceSuivante(UsineDePiece.genererPiece());
        puits.setPieceSuivante(UsineDePiece.genererPiece()); // Nécessaire pour afficher la VuePiece

        JFrame frame = new JFrame("Puits");
        frame.setContentPane(vuePuits);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void testConstructeurPuitsTaille() {
        Puits puits = new Puits(8, 18);
        VuePuits vuePuits = new VuePuits(puits, 20);

        UsineDePiece.setMode(UsineDePiece.Mode.TEST);
        puits.setPieceSuivante(UsineDePiece.genererPiece());
        puits.setPieceSuivante(UsineDePiece.genererPiece()); // Idem ici

        JFrame frame = new JFrame("Puits et taille");
        frame.setContentPane(vuePuits);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VuePuitsAffichageTest::new);
    }
}