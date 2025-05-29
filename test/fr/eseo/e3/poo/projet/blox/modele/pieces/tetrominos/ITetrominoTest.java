package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ITetrominoTest {

    @Test
    public void testNombreDElements() {
        ITetromino piece = new ITetromino(Couleur.CYAN);
        assertEquals(4, piece.getElements().size(), "Une pièce I doit contenir 4 éléments");
    }

    @Test
    public void testCoordonneesInitiales() {
        ITetromino piece = new ITetromino(Couleur.CYAN);
        List<Element> elements = piece.getElements();

        assertEquals(new Coordonnees(4, 0), elements.get(0).getCoordonnees());
        assertEquals(new Coordonnees(4, 1), elements.get(1).getCoordonnees());
        assertEquals(new Coordonnees(4, 2), elements.get(2).getCoordonnees());
        assertEquals(new Coordonnees(4, 3), elements.get(3).getCoordonnees());
    }

    @Test
    public void testCouleur() {
        ITetromino piece = new ITetromino(Couleur.CYAN);
        for (Element e : piece.getElements()) {
            assertEquals(Couleur.CYAN, e.getCouleur());
        }
    }

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

    @Test
    public void testDeplacementInvalideIllegalArgument() {
        ITetromino piece = new ITetromino(Couleur.ROUGE);
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(0, -1));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(2, 0));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(1, 1));
    }

    @Test
    public void testBloxExceptionCollision() {
        Puits puits = new Puits(10, 20);
        Tas tas = puits.getTas();
        tas.ajouterPiece(new ITetromino(new Coordonnees(4, 4), Couleur.BLEU));

        ITetromino piece = new ITetromino(new Coordonnees(4, 3), Couleur.VERT);
        piece.setPuits(puits);

        assertThrows(BloxException.class, () -> piece.deplacerDe(0, 1), "Une collision avec le tas doit lever BloxException");
    }

    @Test
    public void testBloxExceptionSortiePuits() {
        Puits puits = new Puits(10, 20);
        ITetromino piece = new ITetromino(new Coordonnees(0, 0), Couleur.VERT);
        piece.setPuits(puits);

        assertThrows(BloxException.class, () -> piece.deplacerDe(-1, 0), "Une sortie du puits à gauche doit lever BloxException");
    }

    @Test
    public void testRotationHoraireForme() throws BloxException {
        Puits puits = new Puits(10, 20);
        ITetromino piece = new ITetromino(new Coordonnees(5, 5), Couleur.VERT); // Position centrale
        piece.setPuits(puits);

        for (int i = 0; i < 4; i++) {
            piece.tourner(true);
        }

        List<Element> elements = piece.getElements();
        boolean estVerticale = elements.stream()
                .map(e -> e.getCoordonnees().getAbscisse())
                .distinct()
                .count() == 1;

        assertTrue(estVerticale, "Après 4 rotations, la pièce I doit être verticale.");
    }

    @Test
    public void testRotationAntiHoraire() throws BloxException {
        Puits puits = new Puits(10, 20);
        ITetromino piece = new ITetromino(new Coordonnees(5, 5), Couleur.BLEU); // Position centrale
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

        assertEquals(initiales, apres360, "Après 4 rotations anti-horaires, la pièce doit revenir à sa position initiale.");
    }

}