package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour la pièce TTetromino (forme en T).
 */
public class TTetrominoTest {

    /**
     * Vérifie que la pièce T contient bien 4 éléments.
     */
    @Test
    public void testNombreDElements() {
        TTetromino piece = new TTetromino(Couleur.VIOLET);
        assertEquals(4, piece.getElements().size(), "La pièce T doit contenir 4 éléments.");
    }

    /**
     * Vérifie les coordonnées initiales de la pièce en position par défaut.
     */
    @Test
    public void testCoordonneesInitiales() {
        TTetromino piece = new TTetromino(Couleur.VIOLET);
        List<Coordonnees> coords = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        assertTrue(coords.contains(new Coordonnees(3, 1)));
        assertTrue(coords.contains(new Coordonnees(4, 1))); // pivot
        assertTrue(coords.contains(new Coordonnees(5, 1)));
        assertTrue(coords.contains(new Coordonnees(4, 2)));
    }

    /**
     * Vérifie que tous les éléments de la pièce ont la couleur attendue.
     */
    @Test
    public void testCouleurElements() {
        TTetromino piece = new TTetromino(Couleur.JAUNE);
        for (Element e : piece.getElements()) {
            assertEquals(Couleur.JAUNE, e.getCouleur(), "Tous les éléments doivent être jaunes.");
        }
    }

    /**
     * Vérifie qu’un déplacement valide fonctionne correctement.
     */
    /**
     * Vérifie que la pièce T peut se déplacer vers la droite et vers le bas dans le puits.
     */
    @Test
    public void testDeplacementValide() throws BloxException {
        // Création du puits vide
        Puits puits = new Puits(10, 20);

        // Création de la pièce T et assignation du puits
        TTetromino piece = new TTetromino(Couleur.VERT);
        piece.setPuits(puits);

        // On place la pièce à une position connue (4, 1)
        piece.setPosition(4, 1);

        // Déplacement vers la droite puis vers le bas
        piece.deplacerDe(1, 0); // droite
        piece.deplacerDe(0, 1); // bas

        // Coordonnées attendues après déplacement
        List<Coordonnees> attendues = piece.getFormeRelative(5, 2); // pivot déplacé en (5,2)

        // Coordonnées réelles après déplacement
        List<Coordonnees> obtenues = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        // Vérification
        assertEquals(attendues.size(), obtenues.size(), "Le nombre d’éléments doit être identique.");

        for (Coordonnees c : attendues) {
            assertTrue(obtenues.contains(c), "Coordonnée attendue manquante : " + c);
        }
    }

    /**
     * Vérifie qu’un déplacement invalide génère une IllegalArgumentException.
     */
    @Test
    public void testDeplacementInvalide() {
        TTetromino piece = new TTetromino(Couleur.ROUGE);
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(2, 2));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(0, -1));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(-1, -1));
    }

    /**
     * Vérifie qu’une rotation horaire puis antihoraire ramène à la position initiale.
     */
    @Test
    public void testRotationReversible() throws BloxException {
        Puits puits = new Puits(10, 20);
        TTetromino piece = new TTetromino(Couleur.VERT);
        piece.setPuits(puits);
        piece.setPosition(4, 5);

        List<Coordonnees> avant = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        piece.tourner(true);  // horaire
        piece.tourner(false); // antihoraire

        List<Coordonnees> apres = piece.getElements().stream()
                .map(Element::getCoordonnees)
                .collect(Collectors.toList());

        assertEquals(avant, apres, "La rotation horaire suivie d'une rotation antihoraire doit restituer la forme d'origine.");
    }

    /**
     * Vérifie qu’une rotation hors limites du puits lève une BloxException.
     */
    @Test
    public void testRotationSortiePuits() {
        Puits puits = new Puits(10, 20);
        TTetromino piece = new TTetromino(Couleur.BLEU);
        piece.setPuits(puits);
        piece.setPosition(0, 0);

        assertThrows(BloxException.class, () -> piece.tourner(true));
    }

    /**
     * Vérifie que la collision lors de la rotation déclenche une BloxException.
     */
    @Test
    public void testRotationCollisionTas() {
        Puits puits = new Puits(10, 20);
        TTetromino piece = new TTetromino(Couleur.BLEU);
        piece.setPuits(puits);
        piece.setPosition(4, 5); // Position connue

        // En analysant la rotation horaire, on trouve qu’un bloc arrive à (4,4)
        // On bloque cette position avec un élément du tas
        puits.getTas().getElements().add(new Element(new Coordonnees(4, 4), Couleur.ROUGE));

        assertThrows(BloxException.class, () -> piece.tourner(true));
    }
}