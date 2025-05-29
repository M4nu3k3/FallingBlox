package fr.eseo.e3.poo.projet.blox.modele;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoordonneesTest {

    @Test
    public void testConstructeurEtGetters() {
        Coordonnees coord = new Coordonnees(3, 5);
        assertEquals(3, coord.getAbscisse());
        assertEquals(5, coord.getOrdonnee());
    }

    @Test
    public void testSetters() {
        Coordonnees coord = new Coordonnees(0, 0);
        coord.setAbscisse(7);
        coord.setOrdonnee(9);
        assertEquals(7, coord.getAbscisse());
        assertEquals(9, coord.getOrdonnee());
    }

    @Test
    public void testToString() {
        Coordonnees coord = new Coordonnees(2, 4);
        assertEquals("(2, 4)", coord.toString());
    }

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
