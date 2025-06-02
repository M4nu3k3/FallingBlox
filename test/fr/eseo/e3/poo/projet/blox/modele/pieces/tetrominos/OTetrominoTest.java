package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour la pièce OTetromino
 */
public class OTetrominoTest {

    /**
     * Vérifie que la pièce contient bien 4 éléments
     */
    @Test
    public void testNombreDElements() {
        OTetromino piece = new OTetromino(Couleur.BLEU);
        assertEquals(4, piece.getElements().size(), "Une pièce O doit toujours contenir 4 éléments.");
    }

    /**
     * Vérifie les coordonnées initiales et la couleur des éléments
     */
    @Test
    public void testCoordonneesInitialesEtCouleur() {
        OTetromino piece = new OTetromino(Couleur.VERT);
        List<Element> elements = piece.getElements();

        assertEquals(new Coordonnees(4, 0), elements.get(0).getCoordonnees());
        assertEquals(new Coordonnees(5, 0), elements.get(1).getCoordonnees());
        assertEquals(new Coordonnees(4, 1), elements.get(2).getCoordonnees());
        assertEquals(new Coordonnees(5, 1), elements.get(3).getCoordonnees());

        for (Element element : elements) {
            assertEquals(Couleur.VERT, element.getCouleur(), "Tous les éléments doivent être de la même couleur.");
        }
    }

    /**
     * Vérifie le déplacement valide dans un puits
     */
    @Test
    public void testDeplacementValide() throws BloxException {
        Puits puits = new Puits(10, 20);
        OTetromino piece = new OTetromino(Couleur.ROUGE);
        piece.setPuits(puits);

        piece.deplacerDe(1, 0);
        piece.deplacerDe(0, 1);

        List<Element> elements = piece.getElements();
        assertEquals(new Coordonnees(5, 1), elements.get(0).getCoordonnees());
        assertEquals(new Coordonnees(6, 1), elements.get(1).getCoordonnees());
        assertEquals(new Coordonnees(5, 2), elements.get(2).getCoordonnees());
        assertEquals(new Coordonnees(6, 2), elements.get(3).getCoordonnees());
    }

    /**
     * Vérifie que les déplacements invalides génèrent une IllegalArgumentException
     */
    @Test
    public void testDeplacementInvalideIllegalArgument() {
        Puits puits = new Puits(10, 20);
        OTetromino piece = new OTetromino(Couleur.JAUNE);
        piece.setPuits(puits);

        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(0, -1));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(2, 2));
        assertThrows(IllegalArgumentException.class, () -> piece.deplacerDe(1, 1));
    }

    /**
     * Vérifie qu'une sortie du puits déclenche une exception
     */
    @Test
    public void testSortiePuitsDeclencheException() {
        Puits puits = new Puits(10, 20);
        OTetromino piece = new OTetromino(new Coordonnees(0, 0), Couleur.BLEU);
        piece.setPuits(puits);

        assertThrows(BloxException.class, () -> piece.deplacerDe(-1, 0));
    }

    /**
     * Vérifie qu'une collision avec une pièce déjà présente déclenche une exception
     */
    @Test
    public void testCollisionDeclencheException() {
        Puits puits = new Puits(10, 20);
        OTetromino pieceFixe = new OTetromino(new Coordonnees(4, 4), Couleur.VIOLET);
        puits.getTas().ajouterPiece(pieceFixe);

        OTetromino pieceMobile = new OTetromino(new Coordonnees(4, 3), Couleur.ORANGE);
        pieceMobile.setPuits(puits);

        assertThrows(BloxException.class, () -> pieceMobile.deplacerDe(0, 1));
    }

    /**
     * Vérifie que les rotations d'un OTetromino ne modifient pas ses coordonnées
     */
    @Test
    public void testRotationInerte() throws BloxException {
        OTetromino piece = new OTetromino(Couleur.CYAN);
        List<Coordonnees> avantRotation = new ArrayList<>();
        for (Element e : piece.getElements()) {
            Coordonnees c = e.getCoordonnees();
            avantRotation.add(new Coordonnees(c.getAbscisse(), c.getOrdonnee()));
        }

        piece.tourner(true);   // sens horaire
        piece.tourner(false);  // sens antihoraire

        List<Element> apresRotation = piece.getElements();
        for (int i = 0; i < 4; i++) {
            assertEquals(avantRotation.get(i), apresRotation.get(i).getCoordonnees(),
                    "La rotation d’un OTetromino ne doit pas modifier ses coordonnées.");
        }
    }
}