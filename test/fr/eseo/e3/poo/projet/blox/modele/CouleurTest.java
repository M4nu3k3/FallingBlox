package fr.eseo.e3.poo.projet.blox.modele;

import org.junit.jupiter.api.Test;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

public class CouleurTest {

    @Test
    public void testNombreDeCouleurs() {
        assertEquals(7, Couleur.values().length);
    }

    @Test
    public void testNomCouleur() {
        assertEquals("ROUGE", Couleur.ROUGE.name());
    }

    @Test
    public void testNomAutresCouleurs() {
        assertEquals("ORANGE", Couleur.ORANGE.name());
        assertEquals("BLEU", Couleur.BLEU.name());
        assertEquals("VERT", Couleur.VERT.name());
        assertEquals("JAUNE", Couleur.JAUNE.name());
        assertEquals("CYAN", Couleur.CYAN.name());
    }

    @Test
    public void testCouleurPourAffichage() {
        assertEquals(Color.RED, Couleur.ROUGE.getCouleurPourAffichage());
        assertEquals(Color.ORANGE, Couleur.ORANGE.getCouleurPourAffichage());
        assertEquals(Color.BLUE, Couleur.BLEU.getCouleurPourAffichage());
        assertEquals(Color.GREEN, Couleur.VERT.getCouleurPourAffichage());
        assertEquals(Color.YELLOW, Couleur.JAUNE.getCouleurPourAffichage());
        assertEquals(Color.CYAN, Couleur.CYAN.getCouleurPourAffichage());
    }

    @Test
    public void testNonNullCouleurPourAffichage() {
        for (Couleur c : Couleur.values()) {
            assertNotNull(c.getCouleurPourAffichage(), "La couleur Swing doit être non nulle pour " + c.name());
        }
    }
}
