package fr.eseo.e3.poo.projet.blox.modele;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests pour valider la génération de pièces via l'UsineDePiece.
 */
public class UsineDePieceTest {

    // Répète le test pour vérifier que la génération produit des résultats valides et cohérents
    @RepeatedTest(10)
    public void testGenererTetromino() {
        Tetromino tetromino = UsineDePiece.genererTetromino();

        // Vérifie que la pièce n'est pas null et contient bien 4 éléments
        assertNotNull(tetromino, "Le tetromino généré ne doit pas être null");
        assertEquals(4, tetromino.getElements().size(), "Un tetromino doit contenir 4 éléments");

        // Chaque élément doit avoir une couleur
        for (Element e : tetromino.getElements()) {
            assertNotNull(e.getCouleur(), "Chaque élément doit avoir une couleur");
        }

        // Vérifie que la pièce est bien positionnée à la coordonnée d'origine attendue
        assertEquals(2, tetromino.getElements().get(0).getCoordonnees().getAbscisse(),
                "Le tetromino doit être positionné en x = 2");
        assertEquals(3, tetromino.getElements().get(0).getCoordonnees().getOrdonnee(),
                "Le tetromino doit être positionné en y = 3");
    }
}