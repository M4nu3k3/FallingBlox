package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test JUnit pour le comportement de la pièce LTetromino.
 */
public class LTetrominoTest {

    @Test
    public void testNombreDElements() {
        LTetromino piece = new LTetromino(Couleur.JAUNE);
        assertEquals(4, piece.getElements().size(), "La pièce L doit avoir 4 éléments.");
    }

    @Test
    public void testCoordonneesInitiales() {
        LTetromino piece = new LTetromino(Couleur.JAUNE);
        List<Coordonnees> coords = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        assertTrue(coords.contains(new Coordonnees(3, 1)));
        assertTrue(coords.contains(new Coordonnees(4, 1)));
        assertTrue(coords.contains(new Coordonnees(5, 1)));
        assertTrue(coords.contains(new Coordonnees(5, 2)));
    }

    @Test
    public void testCouleurElements() {
        LTetromino piece = new LTetromino(Couleur.ORANGE);
        for (Element e : piece.getElements()) {
            assertEquals(Couleur.ORANGE, e.getCouleur());
        }
    }

    @Test
    public void testDeplacementValide() throws BloxException {
        Puits puits = new Puits(10, 20);
        LTetromino piece = new LTetromino(Couleur.BLEU);
        piece.setPuits(puits);
        piece.setPosition(4, 1);

        piece.deplacerDe(1, 0);
        piece.deplacerDe(0, 1);

        List<Coordonnees> attendues = piece.getFormeRelative(5, 2);
        List<Coordonnees> obtenues = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        assertEquals(attendues.size(), obtenues.size());

        for (Coordonnees c : attendues) {
            assertTrue(obtenues.contains(c));
        }
    }

    @Test
    public void testDeplacementInvalide() {
        LTetromino piece = new LTetromino(Couleur.VERT);
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(1, 1));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(0, -1));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(2, 0));
    }

    @Test
    public void testCollisionAvecTas() {
        Puits puits = new Puits(10, 20);
        LTetromino fixe = new LTetromino(Couleur.ROUGE);
        fixe.setPosition(4, 3);
        puits.getTas().ajouterPiece(fixe);

        LTetromino mobile = new LTetromino(Couleur.BLEU);
        mobile.setPosition(4, 2);
        mobile.setPuits(puits);

        assertThrows(BloxException.class, () -> mobile.deplacerDe(0, 1));
    }

    @Test
    public void testSortiePuits() {
        Puits puits = new Puits(10, 20);
        LTetromino piece = new LTetromino(Couleur.CYAN);
        piece.setPosition(0, 0);
        piece.setPuits(puits);

        assertThrows(BloxException.class, () -> piece.deplacerDe(-1, 0));
    }

    @Test
    public void testRotationHoraireComplete() throws BloxException {
        Puits puits = new Puits(10, 20);
        LTetromino piece = new LTetromino(Couleur.JAUNE);
        piece.setPosition(5, 5);
        piece.setPuits(puits);

        List<Coordonnees> initiales = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        for (int i = 0; i < 4; i++) {
            piece.tourner(true);
        }

        List<Coordonnees> finales = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        assertEquals(initiales, finales);
    }

    @Test
    public void testRotationCollisionTas() {
        Puits puits = new Puits(10, 20);
        LTetromino pieceFixe = new LTetromino(Couleur.ROUGE);
        pieceFixe.setPosition(5, 6);
        puits.getTas().ajouterPiece(pieceFixe);

        LTetromino pieceMobile = new LTetromino(Couleur.BLEU);
        pieceMobile.setPosition(5, 5);
        pieceMobile.setPuits(puits);

        assertThrows(BloxException.class, () -> pieceMobile.tourner(true));
    }
}