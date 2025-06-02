package fr.eseo.e3.poo.projet.blox.modele;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.*;

import java.util.Random;

/**
 * Usine de création de pièces du jeu Blox.
 * Permet de générer des pièces selon différents modes :
 * TEST : toujours la même pièce (I rouge) pour les tests
 * CYCLIQUE : pièce et couleur en cycle prédéfini
 * ALEATOIRE : pièce et couleur aléatoires
 */
public class UsineDePiece {

    /**
     * Modes disponibles pour la génération des pièces
     */
    public enum Mode {
        ALEATOIRE, CYCLIQUE, TEST
    }

    private static Mode mode = Mode.ALEATOIRE;  // Mode par défaut
    private static int indexCyclique = 0;       // Index pour mode cyclique

    private static final Couleur[] COULEURS = Couleur.values();
    private static final Class<?>[] TETROMINOS = {
            ITetromino.class,
            OTetromino.class,
            TTetromino.class,
            LTetromino.class,
            JTetromino.class,
            ZTetromino.class,
            STetromino.class
    };

    private static final Random RANDOM = new Random();

    /**
     * Définit le mode de génération et réinitialise l'index cyclique
     */
    public static void setMode(Mode nouveauMode) {
        mode = nouveauMode;
        indexCyclique = 0;
    }

    /**
     * Retourne le mode courant de génération
     */
    public static Mode getMode() {
        return mode;
    }

    /**
     * Génère un tétromino pour tests (I rouge à position fixe)
     */
    public static Tetromino genererTetromino() {
        Tetromino tetromino = new ITetromino(Couleur.ROUGE);
        tetromino.setPosition(2, 3); // Position fixe pour visibilité tests
        return tetromino;
    }

    /**
     * Génère une pièce selon le mode courant, sans puits associé
     */
    public static Piece genererPiece() {
        return genererPiece(null);
    }

    /**
     * Génère une pièce selon le mode courant, en lui associant un puits
     */
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

    /**
     * Crée une instance de pièce en utilisant la réflexion, avec un fallback vers ITetromino en cas d'erreur
     */
    private static Piece creerPiece(int index, Couleur couleur) {
        try {
            return (Piece) TETROMINOS[index].getConstructor(Couleur.class).newInstance(couleur);
        } catch (Exception e) {
            e.printStackTrace();
            return new ITetromino(couleur);
        }
    }
}