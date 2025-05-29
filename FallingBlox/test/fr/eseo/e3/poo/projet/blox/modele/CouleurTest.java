package fr.eseo.e3.poo.projet.blox.modele;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour l'enum Couleur.
 */
public class CouleurTest {

    // Vérifie que l'enum contient bien 7 couleurs
    @Test
    public void testNombreDeCouleurs() {
        assertEquals(7, Couleur.values().length);
    }

    // Vérifie le nom de la première couleur
    @Test
    public void testNomCouleur() {
        assertEquals("ROUGE", Couleur.ROUGE.name());
    }

    // Vérifie les noms des autres couleurs
    @Test
    public void testNomAutresCouleurs() {
        assertEquals("ORANGE", Couleur.ORANGE.name());
        assertEquals("BLEU", Couleur.BLEU.name());
        assertEquals("VERT", Couleur.VERT.name());
        assertEquals("JAUNE", Couleur.JAUNE.name());
        assertEquals("CYAN", Couleur.CYAN.name());
    }

    // Vérifie que chaque couleur est bien mappée à sa couleur Swing
    @Test
    public void testCouleurPourAffichage() {
        assertEquals(Color.RED, Couleur.ROUGE.getCouleurPourAffichage());
        assertEquals(Color.ORANGE, Couleur.ORANGE.getCouleurPourAffichage());
        assertEquals(Color.BLUE, Couleur.BLEU.getCouleurPourAffichage());
        assertEquals(Color.GREEN, Couleur.VERT.getCouleurPourAffichage());
        assertEquals(Color.YELLOW, Couleur.JAUNE.getCouleurPourAffichage());
        assertEquals(Color.CYAN, Couleur.CYAN.getCouleurPourAffichage());
    }

    // Vérifie qu'aucune couleur de l'enum n'est mappée à null
    @Test
    public void testNonNullCouleurPourAffichage() {
        for (Couleur c : Couleur.values()) {
            assertNotNull(c.getCouleurPourAffichage(), "La couleur Swing doit être non nulle pour " + c.name());
        }
    }
}