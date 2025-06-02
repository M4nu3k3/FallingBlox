package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe ITetromino
 */
public class ITetrominoTest {

    /**
     * Vérifie que la pièce contient bien 4 éléments
     */
    @Test
    public void testNombreDElements() {
        ITetromino piece = new ITetromino(Couleur.CYAN);
        assertEquals(4, piece.getElements().size(), "Une pièce I doit contenir 4 éléments.");
    }

    /**
     * Vérifie les coordonnées initiales de la pièce
     */
    @Test
    public void testCoordonneesInitiales() {
        ITetromino piece = new ITetromino(Couleur.CYAN);
        List<Element> elements = piece.getElements();

        assertEquals(new Coordonnees(4, 0), elements.get(0).getCoordonnees());
        assertEquals(new Coordonnees(4, 1), elements.get(1).getCoordonnees()); // pivot
        assertEquals(new Coordonnees(4, 2), elements.get(2).getCoordonnees());
        assertEquals(new Coordonnees(4, 3), elements.get(3).getCoordonnees());
    }

    /**
     * Vérifie que tous les éléments ont la bonne couleur
     */
    @Test
    public void testCouleurElements() {
        ITetromino piece = new ITetromino(Couleur.CYAN);
        for (Element e : piece.getElements()) {
            assertEquals(Couleur.CYAN, e.getCouleur());
        }
    }

    /**
     * Vérifie le déplacement valide d'une pièce dans un puits
     */
    @Test
    public void testDeplacementValide() throws BloxException {
        Puits puits = new Puits(10, 20);
        ITetromino piece = new ITetromino(Couleur.BLEU);
        piece.setPuits(puits);

        piece.deplacerDe(1, 0); // droite
        piece.deplacerDe(0, 1); // bas

        List<Element> elements = piece.getElements();
        assertEquals(new Coordonnees(5, 1), elements.get(0).getCoordonnees());
        assertEquals(new Coordonnees(5, 2), elements.get(1).getCoordonnees());
        assertEquals(new Coordonnees(5, 3), elements.get(2).getCoordonnees());
        assertEquals(new Coordonnees(5, 4), elements.get(3).getCoordonnees());
    }

    /**
     * Vérifie que les déplacements invalides lèvent une IllegalArgumentException
     */
    @Test
    public void testDeplacementInvalide() {
        ITetromino piece = new ITetromino(Couleur.JAUNE);

        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(0, -1));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(1, 1));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(2, 0));
    }

    /**
     * Vérifie qu'une collision déclenche une BloxException
     */
    @Test
    public void testCollisionAvecTas() {
        Puits puits = new Puits(10, 20);
        Tas tas = puits.getTas();
        tas.ajouterPiece(new ITetromino(new Coordonnees(4, 4), Couleur.VERT));

        ITetromino piece = new ITetromino(new Coordonnees(4, 3), Couleur.ROUGE);
        piece.setPuits(puits);

        assertThrows(BloxException.class, () -> piece.deplacerDe(0, 1));
    }

    /**
     * Vérifie qu'une sortie du puits déclenche une BloxException
     */
    @Test
    public void testSortiePuitsDeclencheException() {
        Puits puits = new Puits(10, 20);
        ITetromino piece = new ITetromino(new Coordonnees(0, 0), Couleur.ORANGE);
        piece.setPuits(puits);

        assertThrows(BloxException.class, () -> piece.deplacerDe(-1, 0));
    }

    /**
     * Vérifie que 4 rotations horaires ramènent la pièce à sa forme initiale
     */
    @Test
    public void testRotationHoraire360() throws BloxException {
        Puits puits = new Puits(10, 20);
        ITetromino piece = new ITetromino(new Coordonnees(5, 5), Couleur.BLEU);
        piece.setPuits(puits);

        for (int i = 0; i < 4; i++) {
            piece.tourner(true);
        }

        long abscissesDistinctes = piece.getElements().stream()
                .map(e -> e.getCoordonnees().getAbscisse())
                .distinct()
                .count();

        assertEquals(1, abscissesDistinctes, "La pièce doit être verticale après 4 rotations horaires.");
    }

    /**
     * Vérifie que 4 rotations anti-horaires ramènent la pièce à sa position initiale
     */
    @Test
    public void testRotationAntiHoraire360() throws BloxException {
        Puits puits = new Puits(10, 20);
        ITetromino piece = new ITetromino(new Coordonnees(5, 5), Couleur.CYAN);
        piece.setPuits(puits);

        List<Coordonnees> coordInitiales = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        for (int i = 0; i < 4; i++) {
            piece.tourner(false);
        }

        List<Coordonnees> coordFinales = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        assertEquals(coordInitiales, coordFinales, "Après 4 rotations antihoraires, la forme doit être identique.");
    }
}