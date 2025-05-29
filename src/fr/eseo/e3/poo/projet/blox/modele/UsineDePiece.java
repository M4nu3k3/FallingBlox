package fr.eseo.e3.poo.projet.blox.modele;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.OTetromino;

import java.util.Random;

/**
 * Usine permettant de générer des pièces en fonction d’un mode (TEST, CYCLIQUE, ALEATOIRE).
 */
public class UsineDePiece {

    public enum Mode {
        ALEATOIRE, CYCLIQUE, TEST
    }

    private static Mode mode = Mode.ALEATOIRE;
    private static int indexCyclique = 0;

    private static final Couleur[] COULEURS = Couleur.values();
    private static final Class<?>[] TETROMINOS = {
            ITetromino.class,
            OTetromino.class
            // Tu peux ajouter d'autres Tetrominos ici
    };

    private static final Random RANDOM = new Random();

    /* === Mode de fonctionnement === */

    public static void setMode(Mode nouveauMode) {
        mode = nouveauMode;
        indexCyclique = 0;
    }

    public static Mode getMode() {
        return mode;
    }

    /* === Génération directe de test === */

    public static Tetromino genererTetromino() {
        Tetromino tetromino = new ITetromino(Couleur.ROUGE);
        tetromino.setPosition(2, 3); // Pour tests visuels
        return tetromino;
    }

    /* === Génération publique de pièces === */

    public static Piece genererPiece() {
        return genererPiece(null);
    }

    public static Piece genererPiece(Puits puits) {
        Piece piece;

        switch (mode) {
            case TEST:
                piece = new ITetromino(Couleur.ROUGE);
                break;

            case CYCLIQUE:
                piece = creerPiece(indexCyclique % TETROMINOS.length,
                        COULEURS[indexCyclique % COULEURS.length]);
                indexCyclique++;
                break;

            case ALEATOIRE:
            default:
                int indexType = RANDOM.nextInt(TETROMINOS.length);
                Couleur couleur = COULEURS[RANDOM.nextInt(COULEURS.length)];
                piece = creerPiece(indexType, couleur);
                break;
        }

        if (puits != null) {
            piece.setPuits(puits);
        }

        return piece;
    }

    /* === Génération dynamique par réflexion === */

    private static Piece creerPiece(int index, Couleur couleur) {
        try {
            return (Piece) TETROMINOS[index].getConstructor(Couleur.class).newInstance(couleur);
        } catch (Exception e) {
            e.printStackTrace();
            return new ITetromino(couleur); // fallback de secours
        }
    }
}