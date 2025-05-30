package fr.eseo.e3.poo.projet.blox.modele;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test JUnit pour la classe Coordonnees.
 */
public class CoordonneesTest {

    // Vérifie la bonne initialisation via le constructeur et les getters
    @Test
    public void testConstructeurEtGetters() {
        Coordonnees coord = new Coordonnees(3, 5);
        assertEquals(3, coord.getAbscisse());
        assertEquals(5, coord.getOrdonnee());
    }

    // Vérifie le bon fonctionnement des setters
    @Test
    public void testSetters() {
        Coordonnees coord = new Coordonnees(0, 0);
        coord.setAbscisse(7);
        coord.setOrdonnee(9);
        assertEquals(7, coord.getAbscisse());
        assertEquals(9, coord.getOrdonnee());
    }

    // Vérifie la représentation textuelle via toString
    @Test
    public void testToString() {
        Coordonnees coord = new Coordonnees(2, 4);
        assertEquals("(2, 4)", coord.toString());
    }

    // Vérifie les méthodes equals et hashCode
    @Test
    public void testEqualsEtHashCode() {
        Coordonnees a = new Coordonnees(1, 2);
        Coordonnees b = new Coordonnees(1, 2);
        Coordonnees c = new Coordonnees(2, 3);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}