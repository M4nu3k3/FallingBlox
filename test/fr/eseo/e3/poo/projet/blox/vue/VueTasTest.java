package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests unitaires pour la classe VueTas.
 * Elle valide que le tas est affichable sans erreur et que la construction
 * de la classe VueTas est correcte.
 */
public class VueTasTest {

    /**
     * Vérifie que la création d’un objet VueTas est fonctionnelle.
     */
    @Test
    public void testConstructeur() {
        Puits puits = new Puits(10, 20);
        VueTas vueTas = new VueTas(puits, 30);

        assertNotNull(vueTas);
    }

    /**
     * Vérifie que la méthode afficher() ne déclenche aucune exception
     * lors de l'affichage avec un Graphics2D simulé.
     */
    @Test
    public void testAfficherTas() {
        Puits puits = new Puits(10, 20);
        ITetromino piece = new ITetromino(Couleur.BLEU);
        puits.getTas().ajouterPiece(piece);

        VueTas vueTas = new VueTas(puits, 20);

        BufferedImage image = new BufferedImage(300, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        assertDoesNotThrow(() -> vueTas.afficher(g2d));
        g2d.dispose();
    }

    /**
     * Vérifie que l’on peut modifier la taille de VueTas dynamiquement.
     * Ici, on ne peut pas tester getTaille() car il n’existe pas de getter exposé.
     * On vérifie donc via l’absence d’exception et l’effet de bord indirect.
     */
    @Test
    public void testSetTaille() {
        Puits puits = new Puits();
        VueTas vueTas = new VueTas(puits, 25);

        // Aucune exception ne doit être levée
        assertDoesNotThrow(() -> vueTas.setTaille(35));
    }
}