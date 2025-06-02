package fr.eseo.e3.poo.projet.blox.modele;

import fr.eseo.e3.poo.projet.blox.controleur.Gravite;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests unitaires pour valider les fonctionnalités de la classe Puits, y compris la gestion du score
 */
public class PuitsTest {

    @Test
    public void testInitialisationScore() {
        Puits puits = new Puits();
        assertEquals(0, puits.getScore(), "Le score initial doit être 0.");
        assertEquals(0, puits.getMeilleurScore(), "Le meilleur score initial doit être 0.");
    }
    @Test
    public void testRejouerResetPuits() {
        Puits puits = new Puits(10, 20);
        puits.reset();
        assertEquals(0, puits.getScore());
        assertNotNull(puits.getPieceSuivante());
        assertEquals(0, puits.getTas().getElements().size());
    }

    @Test
    public void testScoreNeBaisseJamais() {
        Puits puits = new Puits();
        int ancienScore = puits.getScore();

        // Méthode spéciale de test pour simuler une augmentation
        puits.incrementerScorePourTest(50);
        assertTrue(puits.getScore() >= ancienScore, "Le score ne doit jamais baisser.");
        assertTrue(puits.getMeilleurScore() >= ancienScore, "Le meilleur score ne doit jamais baisser.");
    }

    @Test
    public void testResetScore() {
        Puits puits = new Puits();
        puits.incrementerScorePourTest(80);

        assertTrue(puits.getScore() > 0, "Le score doit avoir été incrémenté.");
        puits.resetScore();
        assertEquals(0, puits.getScore(), "Le score doit être remis à zéro après reset.");
    }

    @Test
    public void testResetPuitsMetScoreAZero() {
        Puits puits = new Puits();
        puits.incrementerScorePourTest(42);
        assertTrue(puits.getScore() > 0);

        puits.reset();
        assertEquals(0, puits.getScore(), "Le score doit être réinitialisé après reset du puits.");
    }
}