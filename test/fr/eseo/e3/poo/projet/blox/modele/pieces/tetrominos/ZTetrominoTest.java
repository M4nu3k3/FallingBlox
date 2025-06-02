package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests unitaires pour la pièce ZTetromino
 */
public class ZTetrominoTest {

    /**
     * Vérifie que la pièce contient bien 4 éléments
     */
    @Test
    public void testNombreDElements() {
        ZTetromino piece = new ZTetromino(Couleur.ROUGE);
        assertEquals(4, piece.getElements().size());
    }

    /**
     * Vérifie les coordonnées initiales relatives à la pièce
     */
    @Test
    public void testCoordonneesInitiales() {
        ZTetromino piece = new ZTetromino(Couleur.ROUGE);
        List<Coordonnees> coords = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        assertTrue(coords.contains(new Coordonnees(3, 1))); // gauche
        assertTrue(coords.contains(new Coordonnees(4, 1))); // pivot
        assertTrue(coords.contains(new Coordonnees(4, 2))); // bas
        assertTrue(coords.contains(new Coordonnees(5, 2))); // bas droite
    }

    /**
     * Vérifie que tous les éléments ont bien la couleur définie
     */
    @Test
    public void testCouleurElements() {
        ZTetromino piece = new ZTetromino(Couleur.JAUNE);
        for (Element e : piece.getElements()) {
            assertEquals(Couleur.JAUNE, e.getCouleur());
        }
    }

    /**
     * Vérifie un déplacement valide de la pièce dans un puits
     */
    @Test
    public void testDeplacementValide() throws BloxException {
        Puits puits = new Puits(10, 20);
        ZTetromino piece = new ZTetromino(Couleur.CYAN);
        piece.setPuits(puits);
        piece.setPosition(4, 2);

        piece.deplacerDe(1, 0); // droite
        piece.deplacerDe(0, 1); // bas

        List<Coordonnees> attendues = piece.getFormeRelative(5, 3);
        List<Coordonnees> obtenues = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        assertEquals(attendues.size(), obtenues.size());
        for (Coordonnees c : attendues) {
            assertTrue(obtenues.contains(c));
        }
    }

    /**
     * Vérifie que des déplacements invalides lèvent une IllegalArgumentException
     */
    @Test
    public void testDeplacementInvalide() {
        ZTetromino piece = new ZTetromino(Couleur.BLEU);
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(2, 0));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(0, -1));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(1, 1));
    }

    /**
     * Vérifie qu'une sortie du puits déclenche une BloxException
     */
    @Test
    public void testSortiePuits() {
        Puits puits = new Puits(10, 20);
        ZTetromino piece = new ZTetromino(Couleur.VIOLET);
        piece.setPosition(0, 0);
        piece.setPuits(puits);

        assertThrows(BloxException.class, () -> piece.deplacerDe(-1, 0));
    }

    /**
     * Vérifie qu'une collision avec un tas empêche le déplacement
     */
    @Test
    public void testCollisionAvecTas() {
        Puits puits = new Puits(10, 20);
        ZTetromino pieceFixe = new ZTetromino(Couleur.ORANGE);
        pieceFixe.setPosition(4, 3);
        puits.getTas().ajouterPiece(pieceFixe);

        ZTetromino pieceMobile = new ZTetromino(Couleur.BLEU);
        pieceMobile.setPosition(4, 2);
        pieceMobile.setPuits(puits);

        assertThrows(BloxException.class, () -> pieceMobile.deplacerDe(0, 1));
    }

    /**
     * Vérifie qu'une rotation complète (4 fois) restitue la forme d'origine
     */
    @Test
    public void testRotationComplete() throws BloxException {
        Puits puits = new Puits(10, 20);
        ZTetromino piece = new ZTetromino(Couleur.JAUNE);
        piece.setPuits(puits);
        piece.setPosition(5, 5);

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
     * Vérifie qu’une tentative de rotation entraînant une collision lève une exception
     */
    @Test
    public void testRotationCollisionTas() {
        Puits puits = new Puits(10, 20);
        ZTetromino pieceFixe = new ZTetromino(Couleur.ROUGE);
        pieceFixe.setPosition(5, 6);
        puits.getTas().ajouterPiece(pieceFixe);

        ZTetromino pieceMobile = new ZTetromino(Couleur.VERT);
        pieceMobile.setPosition(5, 5);
        pieceMobile.setPuits(puits);

        assertThrows(BloxException.class, () -> pieceMobile.tourner(true));
    }
}