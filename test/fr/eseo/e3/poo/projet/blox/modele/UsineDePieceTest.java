package fr.eseo.e3.poo.projet.blox.modele;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;
import fr.eseo.e3.poo.projet.blox.modele.UsineDePiece;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

public class UsineDePieceTest {

    @RepeatedTest(10) // Teste la génération de 10 tetrominos différents
    public void testGenererTetromino() {
        Tetromino tetromino = UsineDePiece.genererTetromino();

        assertNotNull(tetromino, "Le tetromino généré ne doit pas être null");
        assertEquals(4, tetromino.getElements().size(), "Un tetromino doit contenir 4 éléments");

        for (Element e : tetromino.getElements()) {
            assertNotNull(e.getCouleur(), "Chaque élément doit avoir une couleur");
        }

        // Vérifie que la pièce a bien été placée à la position par défaut (2, 3)
        assertEquals(2, tetromino.getElements().get(0).getCoordonnees().getAbscisse(),
                "Le tetromino doit être positionné en x = 2");
        assertEquals(3, tetromino.getElements().get(0).getCoordonnees().getOrdonnee(),
                "Le tetromino doit être positionné en y = 3");
    }
}
