package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests pour vérifier les comportements de PanneauInformation :
 * - affichage de la pièce suivante
 * - mise à jour du score
 */
public class PanneauInformationTest {

    private Puits puits;
    private PanneauInformation panneau;

    @BeforeEach
    public void setUp() {
        puits = new Puits();
        panneau = new PanneauInformation(puits);
    }

    /**
     * Vérifie que la mise à jour du score modifie bien les labels dans le panneau.
     */
    @Test
    public void testMiseAJourScore() {
        puits.resetScore();

        // Simule une montée en score
        puits.incrementerScorePourTest(1);  // méthode à ajouter dans Puits

        for (int i = 0; i < panneau.getComponentCount(); i++) {
            java.awt.Component c = panneau.getComponent(i);
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                if ("scoreLabel".equals(label.getName())) {
                    assertEquals("Score : 1", label.getText());
                }
                if ("bestScoreLabel".equals(label.getName())) {
                    assertEquals("Meilleur : 1", label.getText());
                }
            }
        }
    }

    /**
     * Vérifie que l'affichage de la pièce suivante met bien à jour les éléments du panneau.
     */
    @Test
    public void testAffichagePieceSuivante() {
        // Génère une pièce
        Piece piece = UsineDePiece.genererPiece(puits);

        // Simule la mise à jour dans le modèle
        puits.setPieceSuivante(piece);

        // Accède au champ "elements" de PanneauInformation via réflexion (ou adapter ton getter si dispo)
        try {
            java.lang.reflect.Field field = panneau.getClass().getDeclaredField("elements");
            field.setAccessible(true);
            java.util.List<?> elements = (java.util.List<?>) field.get(panneau);

            assertNotNull(elements, "Les éléments de la pièce suivante doivent être définis");
            assertEquals(4, elements.size(), "La pièce suivante doit contenir 4 éléments");

        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Erreur d’accès au champ privé 'elements' dans PanneauInformation : " + e.getMessage());
        }
    }
}