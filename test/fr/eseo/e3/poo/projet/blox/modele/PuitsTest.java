package fr.eseo.e3.poo.projet.blox.modele;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos.ITetromino;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests unitaires pour la classe Puits.
 */
public class PuitsTest {

    // Vérifie les valeurs par défaut du constructeur sans paramètre
    @Test
    public void testConstructeurParDefaut() {
        Puits puits = new Puits();
        assertEquals(Puits.LARGEUR_PAR_DEFAUT, puits.getLargeur());
        assertEquals(Puits.PROFONDEUR_PAR_DEFAUT, puits.getProfondeur());
        assertNotNull(puits.getTas());
        assertEquals(0, puits.getTas().getElements().size());
    }

    // Vérifie la construction avec largeur et profondeur spécifiées
    @Test
    public void testConstructeurAvecLargeurEtProfondeur() {
        Puits puits = new Puits(8, 22);
        assertEquals(8, puits.getLargeur());
        assertEquals(22, puits.getProfondeur());
    }

    // Vérifie que la largeur hors bornes génère une exception
    @Test
    public void testSetLargeurInvalide() {
        Puits puits = new Puits();
        assertThrows(IllegalArgumentException.class, () -> puits.setLargeur(4));
        assertThrows(IllegalArgumentException.class, () -> puits.setLargeur(16));
    }

    // Vérifie que la profondeur hors bornes génère une exception
    @Test
    public void testSetProfondeurInvalide() {
        Puits puits = new Puits();
        assertThrows(IllegalArgumentException.class, () -> puits.setProfondeur(9));
        assertThrows(IllegalArgumentException.class, () -> puits.setProfondeur(26));
    }

    // Vérifie l'affectation des pièces actuelle et suivante
    @Test
    public void testSetPieceActuelleEtSuivante() {
        Puits puits = new Puits();
        Piece p1 = new ITetromino(Couleur.ROUGE);
        Piece p2 = new ITetromino(Couleur.BLEU);

        puits.setPieceActuelle(p1);
        assertEquals(p1, puits.getPieceActuelle());

        puits.setPieceSuivante(p2);
        assertEquals(p2, puits.getPieceSuivante());
        assertEquals(p1, puits.getPieceActuelle());
    }

    // Vérifie le constructeur utilisant des paramètres pour pré-remplir le tas
    @Test
    public void testTroisiemeConstructeurAvecTas() {
        Puits puits = new Puits(10, 20, 10, 2);
        assertEquals(10, puits.getLargeur());
        assertEquals(20, puits.getProfondeur());
        assertEquals(10, puits.getTas().getElements().size());
    }

    // Vérifie le toString en l'absence de pièces
    @Test
    public void testToStringSansPieces() {
        Puits puits = new Puits();
        String result = puits.toString();
        assertTrue(result.contains("Puits : Dimension"));
        assertTrue(result.contains("Piece Actuelle : <aucune>"));
        assertTrue(result.contains("Piece Suivante : <aucune>"));
    }

    // Vérifie le toString lorsque des pièces sont présentes
    @Test
    public void testToStringAvecPieces() {
        Puits puits = new Puits();
        Piece piece = new ITetromino(Couleur.VERT);
        puits.setPieceActuelle(piece);
        String result = puits.toString();
        assertTrue(result.contains("ITetromino"));
        assertTrue(result.contains("(5")); // coordonnée typique de départ
    }
}