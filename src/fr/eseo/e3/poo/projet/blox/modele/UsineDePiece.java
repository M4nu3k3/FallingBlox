package fr.eseo.e3.poo.projet.blox.modele;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.OTetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;

import java.util.Random;

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

    public static void setMode(Mode nouveauMode) {
        mode = nouveauMode;
        indexCyclique = 0;
    }

    public static Mode getMode() {
        return mode;
    }

    public static Tetromino genererTetromino() {
        Tetromino tetromino = new ITetromino(Couleur.ROUGE);
        tetromino.setPosition(2, 3);  // pour test
        return tetromino;
    }

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

    private static Piece creerPiece(int index, Couleur couleur) {
        try {
            return (Piece) TETROMINOS[index].getConstructor(Couleur.class).newInstance(couleur);
        } catch (Exception e) {
            e.printStackTrace();
            return new ITetromino(couleur); // fallback
        }
    }
}
