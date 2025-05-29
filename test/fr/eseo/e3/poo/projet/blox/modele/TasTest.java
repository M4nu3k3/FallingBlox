package fr.eseo.e3.poo.projet.blox.modele;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TasTest {

    @Test
    public void testAjouterEtContientPiece() {
        Tas tas = new Tas();
        Piece piece = new ITetromino(Couleur.ROUGE);
        assertFalse(tas.contient(piece));
        tas.ajouterPiece(piece);
        assertTrue(tas.contient(piece));
    }

    @Test
    public void testGetElements() {
        Tas tas = new Tas();
        Piece piece = new ITetromino(Couleur.BLEU);
        tas.ajouterPiece(piece);
        assertEquals(4, tas.getElements().size());
    }

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

    @Test
    public void testConstructeurAvecRandomValide() {
        Puits puits = new Puits(10, 20);
        Tas tas = new Tas(puits, 20, 3, new Random(42));
        assertEquals(20, tas.getElements().size());
    }

    @Test
    public void testConstructeurAvecRandomTropGrand() {
        Puits puits = new Puits(10, 20);
        assertThrows(IllegalArgumentException.class, () -> new Tas(puits, 200, 1, new Random()));
    }
}