package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;

import javax.swing.*;

/**
 * Classe de test pour vérifier l'affichage du puits via les différents constructeurs de VuePuits
 */
public class VuePuitsAffichageTest {

    public VuePuitsAffichageTest() {
        testConstructeurPuits();
        testConstructeurPuitsTaille();
    }

    /**
     * Teste le constructeur VuePuits(Puits) avec un puits de dimensions classiques
     */
    private void testConstructeurPuits() {
        Puits puits = new Puits(10, 20);
        VuePuits vuePuits = new VuePuits(puits);

        // Préparation des pièces pour affichage
        UsineDePiece.setMode(UsineDePiece.Mode.TEST);
        puits.setPieceSuivante(UsineDePiece.genererPiece());
        puits.setPieceSuivante(UsineDePiece.genererPiece());

        // Création de la fenêtre de test
        JFrame frame = new JFrame("Puits");
        frame.setContentPane(vuePuits);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Teste le constructeur VuePuits(Puits, int) avec une taille personnalisée
     */
    private void testConstructeurPuitsTaille() {
        Puits puits = new Puits(8, 18);
        VuePuits vuePuits = new VuePuits(puits, 20);

        // Préparation des pièces pour affichage
        UsineDePiece.setMode(UsineDePiece.Mode.TEST);
        puits.setPieceSuivante(UsineDePiece.genererPiece());
        puits.setPieceSuivante(UsineDePiece.genererPiece());

        // Création de la fenêtre de test
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