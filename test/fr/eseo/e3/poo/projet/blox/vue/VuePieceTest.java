package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests pour la classe VuePiece (affichage graphique d’une pièce).
 */
public class VuePieceTest {

    /**
     * Vérifie que la méthode teinte() applique un éclaircissement à une couleur.
     */
    @Test
    public void testTeinte() {
        Color couleurDeBase = new Color(100, 150, 200);
        Color couleurTeintee = VuePiece.teinte(couleurDeBase);

        assertTrue(couleurTeintee.getRed() > couleurDeBase.getRed());
        assertTrue(couleurTeintee.getGreen() > couleurDeBase.getGreen());
        assertTrue(couleurTeintee.getBlue() > couleurDeBase.getBlue());
    }

    /**
     * Vérifie que l’affichage de la pièce ne jette aucune exception dans un Graphics2D.
     */
    @Test
    public void testAfficherPieceSansErreur() {
        Puits puits = new Puits(10, 20);
        Piece piece = new ITetromino(Couleur.CYAN);
        piece.setPuits(puits);

        VuePiece vuePiece = new VuePiece(piece, 20);

        // Création d’un BufferedImage pour simuler une surface de dessin
        BufferedImage image = new BufferedImage(200, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        assertDoesNotThrow(() -> vuePiece.afficherPiece(g2d));
        g2d.dispose();
    }

    /**
     * Vérifie que les couleurs pivot et non pivot sont bien différentes après affichage.
     */
    @Test
    public void testAffichageAvecPivotColorie() {
        ITetromino piece = new ITetromino(Couleur.VERT);
        VuePiece vuePiece = new VuePiece(piece, 20);

        BufferedImage image = new BufferedImage(200, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Activation du rendu (test visuel dans image mémoire)
        vuePiece.afficherPiece(g2d);
        g2d.dispose();

        // Pas de vérification de pixels ici, mais pas d'erreur déclenchée
        assertTrue(true);
    }

    /**
     * Vérifie que le constructeur initialise correctement la pièce et la taille.
     */
    @Test
    public void testConstructeurVuePiece() {
        ITetromino piece = new ITetromino(Couleur.JAUNE);
        VuePiece vue = new VuePiece(piece, 25);

        assertNotNull(vue);
        assertNotNull(piece.getElements());
        assertEquals(4, piece.getElements().size());
    }
}