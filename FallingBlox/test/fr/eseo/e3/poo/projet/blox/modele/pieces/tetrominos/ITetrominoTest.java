package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test JUnit pour le comportement de la pièce ITetromino.
 */
public class ITetrominoTest {

    // Vérifie que la pièce I contient bien 4 éléments
    @Test
    public void testNombreDElements() {
        ITetromino piece = new ITetromino(Couleur.CYAN);
        assertEquals(4, piece.getElements().size(), "Une pièce I doit contenir 4 éléments");
    }

    // Vérifie les coordonnées initiales des éléments de la pièce
    @Test
    public void testCoordonneesInitiales() {
        ITetromino piece = new ITetromino(Couleur.CYAN);
        List<Element> elements = piece.getElements();

        assertEquals(new Coordonnees(4, 0), elements.get(0).getCoordonnees());
        assertEquals(new Coordonnees(4, 1), elements.get(1).getCoordonnees());
        assertEquals(new Coordonnees(4, 2), elements.get(2).getCoordonnees());
        assertEquals(new Coordonnees(4, 3), elements.get(3).getCoordonnees());
    }

    // Vérifie que tous les éléments sont bien de la couleur donnée
    @Test
    public void testCouleur() {
        ITetromino piece = new ITetromino(Couleur.CYAN);
        for (Element e : piece.getElements()) {
            assertEquals(Couleur.CYAN, e.getCouleur());
        }
    }

    // Vérifie le déplacement valide d'une pièce dans le puits
    @Test
    public void testDeplacementValide() throws BloxException {
        Puits puits = new Puits(10, 20);
        ITetromino piece = new ITetromino(Couleur.VERT);
        piece.setPuits(puits);

        piece.deplacerDe(1, 0); // droite
        piece.deplacerDe(0, 1); // bas

        List<Element> elements = piece.getElements();
        assertEquals(new Coordonnees(5, 1), elements.get(0).getCoordonnees());
        assertEquals(new Coordonnees(5, 2), elements.get(1).getCoordonnees());
        assertEquals(new Coordonnees(5, 3), elements.get(2).getCoordonnees());
        assertEquals(new Coordonnees(5, 4), elements.get(3).getCoordonnees());
    }

    // Vérifie que des déplacements invalides lancent une IllegalArgumentException
    @Test
    public void testDeplacementInvalideIllegalArgument() {
        ITetromino piece = new ITetromino(Couleur.ROUGE);
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(0, -1));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(2, 0));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(1, 1));
    }

    // Vérifie que les collisions avec un tas génèrent une BloxException
    @Test
    public void testBloxExceptionCollision() {
        Puits puits = new Puits(10, 20);
        Tas tas = puits.getTas();
        tas.ajouterPiece(new ITetromino(new Coordonnees(4, 4), Couleur.BLEU));

        ITetromino piece = new ITetromino(new Coordonnees(4, 3), Couleur.VERT);
        piece.setPuits(puits);

        assertThrows(BloxException.class, () -> piece.deplacerDe(0, 1),
                "Une collision avec le tas doit lever BloxException");
    }

    // Vérifie que sortir des limites du puits génère une BloxException
    @Test
    public void testBloxExceptionSortiePuits() {
        Puits puits = new Puits(10, 20);
        ITetromino piece = new ITetromino(new Coordonnees(0, 0), Couleur.VERT);
        piece.setPuits(puits);

        assertThrows(BloxException.class, () -> piece.deplacerDe(-1, 0),
                "Une sortie du puits à gauche doit lever BloxException");
    }

    // Vérifie que 4 rotations horaires ramènent la pièce à la position verticale initiale
    @Test
    public void testRotationHoraireForme() throws BloxException {
        Puits puits = new Puits(10, 20);
        ITetromino piece = new ITetromino(new Coordonnees(5, 5), Couleur.VERT);
        piece.setPuits(puits);

        for (int i = 0; i < 4; i++) {
            piece.tourner(true); // sens horaire
        }

        List<Element> elements = piece.getElements();
        boolean estVerticale = elements.stream()
                .map(e -> e.getCoordonnees().getAbscisse())
                .distinct()
                .count() == 1;

        assertTrue(estVerticale, "Après 4 rotations, la pièce I doit être verticale.");
    }

    // Vérifie que 4 rotations anti-horaires ramènent la pièce à sa forme initiale
    @Test
    public void testRotationAntiHoraire() throws BloxException {
        Puits puits = new Puits(10, 20);
        ITetromino piece = new ITetromino(new Coordonnees(5, 5), Couleur.BLEU);
        piece.setPuits(puits);

        List<Coordonnees> initiales = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        for (int i = 0; i < 4; i++) {
            piece.tourner(false); // sens anti-horaire
        }

        List<Coordonnees> apres360 = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        assertEquals(initiales, apres360,
                "Après 4 rotations anti-horaires, la pièce doit revenir à sa position initiale.");
    }
}