package fr.eseo.e3.poo.projet.blox.modele;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests pour valider les fonctionnalités de la classe Tas.
 */
public class TasTest {

    // Vérifie l'ajout d'une pièce et sa présence dans le tas
    @Test
    public void testAjouterEtContientPiece() {
        Tas tas = new Tas();
        Piece piece = new ITetromino(Couleur.ROUGE);

        assertFalse(tas.contient(piece));
        tas.ajouterPiece(piece);
        assertTrue(tas.contient(piece));
    }

    // Vérifie que les éléments d'une pièce ajoutée sont bien intégrés dans le tas
    @Test
    public void testGetElements() {
        Tas tas = new Tas();
        Piece piece = new ITetromino(Couleur.BLEU);

        tas.ajouterPiece(piece);
        assertEquals(4, tas.getElements().size());
    }

    // Vérifie la détection de la présence d'éléments selon leurs coordonnées
    @Test
    public void testContientCoordonnees() {
        Tas tas = new Tas();
        Piece piece = new ITetromino(Couleur.JAUNE);

        tas.ajouterPiece(piece);
        int x = piece.getElements().get(0).getCoordonnees().getAbscisse();
        int y = piece.getElements().get(0).getCoordonnees().getOrdonnee();

        assertTrue(tas.contientCoordonnees(x, y));
        assertFalse(tas.contientCoordonnees(99, 99));
    }

    // Vérifie la génération aléatoire d'un tas valide à partir du constructeur avancé
    @Test
    public void testConstructeurAvecRandomValide() {
        Puits puits = new Puits(10, 20);
        Tas tas = new Tas(puits, 20, 3, new Random(42));

        assertEquals(20, tas.getElements().size());
    }

    // Vérifie que la génération avec un nombre trop élevé d’éléments provoque une exception
    @Test
    public void testConstructeurAvecRandomTropGrand() {
        Puits puits = new Puits(10, 20);

        assertThrows(IllegalArgumentException.class, () ->
                new Tas(puits, 200, 1, new Random()));
    }
}