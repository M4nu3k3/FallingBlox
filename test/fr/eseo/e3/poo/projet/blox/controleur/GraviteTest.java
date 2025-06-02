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
        // Initialisation du puits
        Puits puits = new Puits(10, 20);

        // Création de la vue avant liaison
        VuePuits vuePuits = new VuePuits(puits, VuePuits.TAILLE_PAR_DEFAUT);

        // Activation du mode TEST et génération de la pièce
        UsineDePiece.setMode(UsineDePiece.Mode.TEST);
        puits.setPieceActuelle(UsineDePiece.genererPiece());
        puits.getPieceActuelle().setPosition(5, 5);

        // Réassigner le puits à la vue pour forcer l'enregistrement des PropertyChangeListeners
        vuePuits.setPuits(puits);

        // Création de la fenêtre
        JFrame frame = new JFrame("Test Gravité Automatique");
        frame.setContentPane(vuePuits);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Démarrage de la gravité
        new Gravite(vuePuits, 500);

        // S'assurer que la vue a bien le focus
        vuePuits.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraviteTest::new);
    }
}
