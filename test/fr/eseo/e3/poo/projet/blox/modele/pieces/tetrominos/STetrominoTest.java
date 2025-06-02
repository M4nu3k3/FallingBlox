package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests unitaires pour la pièce STetromino.
 */
public class STetrominoTest {

    /**
     * Vérifie que la pièce S est composée de 4 éléments.
     */
    @Test
    public void testNombreDElements() {
        STetromino piece = new STetromino(Couleur.JAUNE);
        assertEquals(4, piece.getElements().size());
    }

    /**
     * Vérifie les coordonnées initiales relatives à la pièce.
     */
    @Test
    public void testCoordonneesInitiales() {
        STetromino piece = new STetromino(Couleur.JAUNE);
        List<Coordonnees> coords = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        assertTrue(coords.contains(new Coordonnees(5, 1))); // droite
        assertTrue(coords.contains(new Coordonnees(4, 1))); // pivot
        assertTrue(coords.contains(new Coordonnees(4, 2))); // bas pivot
        assertTrue(coords.contains(new Coordonnees(3, 2))); // bas gauche
    }

    /**
     * Vérifie la bonne propagation de la couleur à tous les éléments.
     */
    @Test
    public void testCouleurElements() {
        STetromino piece = new STetromino(Couleur.CYAN);
        for (Element e : piece.getElements()) {
            assertEquals(Couleur.CYAN, e.getCouleur());
        }
    }

    /**
     * Vérifie un déplacement valide dans un puits.
     */
    @Test
    public void testDeplacementValide() throws BloxException {
        Puits puits = new Puits(10, 20);
        STetromino piece = new STetromino(Couleur.VERT);
        piece.setPuits(puits);
        piece.setPosition(4, 1);

        piece.deplacerDe(1, 0); // droite
        piece.deplacerDe(0, 1); // bas

        List<Coordonnees> attendues = piece.getFormeRelative(5, 2);
        List<Coordonnees> obtenues = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        assertEquals(attendues.size(), obtenues.size());
        for (Coordonnees c : attendues) {
            assertTrue(obtenues.contains(c));
        }
    }

    /**
     * Vérifie que les déplacements invalides déclenchent une exception.
     */
    @Test
    public void testDeplacementInvalide() {
        STetromino piece = new STetromino(Couleur.ROUGE);
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(2, 0));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(0, -1));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(1, 1));
    }

    /**
     * Vérifie qu'une collision avec un tas lève une BloxException.
     */
    @Test
    public void testCollisionAvecTas() {
        Puits puits = new Puits(10, 20);
        STetromino pieceFixe = new STetromino(Couleur.ORANGE);
        pieceFixe.setPosition(4, 3);
        puits.getTas().ajouterPiece(pieceFixe);

        STetromino pieceMobile = new STetromino(Couleur.BLEU);
        pieceMobile.setPosition(4, 2);
        pieceMobile.setPuits(puits);

        assertThrows(BloxException.class, () -> pieceMobile.deplacerDe(0, 1));
    }

    /**
     * Vérifie qu'une sortie du puits déclenche une exception.
     */
    @Test
    public void testSortiePuits() {
        Puits puits = new Puits(10, 20);
        STetromino piece = new STetromino(Couleur.VIOLET);
        piece.setPosition(0, 0);
        piece.setPuits(puits);

        assertThrows(BloxException.class, () -> piece.deplacerDe(-1, 0));
    }

    /**
     * Vérifie qu'une rotation complète (4x) restitue la forme initiale.
     */
    @Test
    public void testRotationComplete() throws BloxException {
        Puits puits = new Puits(10, 20);
        STetromino piece = new STetromino(Couleur.JAUNE);
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

    /**
     * Vérifie qu'une rotation en collision déclenche une BloxException.
     */
    @Test
    public void testRotationCollision() {
        Puits puits = new Puits(10, 20);
        STetromino pieceFixe = new STetromino(Couleur.BLEU);
        pieceFixe.setPosition(5, 6);
        puits.getTas().ajouterPiece(pieceFixe);

        STetromino pieceMobile = new STetromino(Couleur.ROUGE);
        pieceMobile.setPosition(5, 5);
        pieceMobile.setPuits(puits);

        assertThrows(BloxException.class, () -> pieceMobile.tourner(true));
    }
}