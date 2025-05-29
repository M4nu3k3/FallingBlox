package fr.eseo.e3.poo.projet.blox.modele;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ElementTest {

    @Test
    public void testConstructeurEtGetters() {
        Coordonnees coord = new Coordonnees(1, 2);
        Element element = new Element(coord, Couleur.ROUGE);

        assertEquals(coord, element.getCoordonnees());
        assertEquals(Couleur.ROUGE, element.getCouleur());
    }

    @Test
    public void testSetters() {
        Element element = new Element(new Coordonnees(0, 0), Couleur.BLEU);
        Coordonnees nouvelleCoord = new Coordonnees(4, 5);
        element.setCoordonnees(nouvelleCoord);
        element.setCouleur(Couleur.VERT);

        assertEquals(nouvelleCoord, element.getCoordonnees());
        assertEquals(Couleur.VERT, element.getCouleur());
    }

    @Test
    public void testToString() {
        Element element = new Element(new Coordonnees(3, 3), Couleur.JAUNE);
        assertEquals("[JAUNE : (3, 3)]", element.toString());
    }

    @Test
    public void testEqualsEtHashCode() {
        Element e1 = new Element(new Coordonnees(1, 1), Couleur.ORANGE);
        Element e2 = new Element(new Coordonnees(1, 1), Couleur.ORANGE);
        Element e3 = new Element(new Coordonnees(2, 2), Couleur.BLEU);

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
        assertNotEquals(e1, e3);
    }
}
