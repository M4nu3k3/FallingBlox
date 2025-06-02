package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests unitaires pour la pièce JTetromino.
 */
public class JTetrominoTest {

    /**
     * Vérifie que la pièce est correctement constituée de 4 éléments.
     */
    @Test
    public void testNombreDElements() {
        JTetromino piece = new JTetromino(Couleur.CYAN);
        assertEquals(4, piece.getElements().size());
    }

    /**
     * Vérifie que les coordonnées initiales sont correctes.
     */
    @Test
    public void testCoordonneesInitiales() {
        JTetromino piece = new JTetromino(Couleur.CYAN);
        List<Coordonnees> coords = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        assertTrue(coords.contains(new Coordonnees(3, 1)));
        assertTrue(coords.contains(new Coordonnees(4, 1)));
        assertTrue(coords.contains(new Coordonnees(5, 1)));
        assertTrue(coords.contains(new Coordonnees(3, 2)));
    }

    /**
     * Vérifie que les couleurs des éléments sont toutes identiques à celle spécifiée
     */
    @Test
    public void testCouleurElements() {
        JTetromino piece = new JTetromino(Couleur.VERT);
        for (Element e : piece.getElements()) {
            assertEquals(Couleur.VERT, e.getCouleur());
        }
    }

    /**
     * Vérifie un déplacement horizontal et vertical sans collision
     */
    @Test
    public void testDeplacementValide() throws BloxException {
        Puits puits = new Puits(10, 20);
        JTetromino piece = new JTetromino(Couleur.ROUGE);
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
     * Vérifie que les déplacements interdits lèvent une IllegalArgumentException
     */
    @Test
    public void testDeplacementInvalide() {
        JTetromino piece = new JTetromino(Couleur.BLEU);
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(2, 0));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(1, 1));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(0, -1));
    }

    /**
     * Vérifie qu'une collision avec un tas lève une BloxException
     */
    @Test
    public void testCollisionAvecTas() {
        Puits puits = new Puits(10, 20);
        JTetromino pieceFixe = new JTetromino(Couleur.JAUNE);
        pieceFixe.setPosition(4, 3);
        puits.getTas().ajouterPiece(pieceFixe);

        JTetromino pieceMobile = new JTetromino(Couleur.VIOLET);
        pieceMobile.setPosition(4, 2);
        pieceMobile.setPuits(puits);

        assertThrows(BloxException.class, () -> pieceMobile.deplacerDe(0, 1));
    }

    /**
     * Vérifie qu'une sortie du puits déclenche une exception
     */
    @Test
    public void testSortiePuits() {
        Puits puits = new Puits(10, 20);
        JTetromino piece = new JTetromino(Couleur.ORANGE);
        piece.setPosition(0, 0);
        piece.setPuits(puits);

        assertThrows(BloxException.class, () -> piece.deplacerDe(-1, 0));
    }

    /**
     * Vérifie que quatre rotations ramènent la pièce à sa forme initiale
     */
    @Test
    public void testRotationComplete() throws BloxException {
        Puits puits = new Puits(10, 20);
        JTetromino piece = new JTetromino(Couleur.CYAN);
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
     * Vérifie qu'une tentative de rotation avec collision génère une BloxException
     */
    @Test
    public void testRotationCollisionTas() {
        Puits puits = new Puits(10, 20);
        JTetromino pieceFixe = new JTetromino(Couleur.ROUGE);
        pieceFixe.setPosition(5, 6);
        puits.getTas().ajouterPiece(pieceFixe);

        JTetromino pieceMobile = new JTetromino(Couleur.BLEU);
        pieceMobile.setPosition(5, 5);
        pieceMobile.setPuits(puits);

        assertThrows(BloxException.class, () -> pieceMobile.tourner(true));
    }
}