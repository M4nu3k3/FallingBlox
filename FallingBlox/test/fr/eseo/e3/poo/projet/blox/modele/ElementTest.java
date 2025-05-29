package fr.eseo.e3.poo.projet.blox.modele;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour la classe Element.
 */
public class ElementTest {

    // Vérifie la construction d'un élément et l'accès à ses coordonnées et sa couleur
    @Test
    public void testConstructeurEtGetters() {
        Coordonnees coord = new Coordonnees(1, 2);
        Element element = new Element(coord, Couleur.ROUGE);

        assertEquals(coord, element.getCoordonnees());
        assertEquals(Couleur.ROUGE, element.getCouleur());
    }

    // Vérifie la modification des coordonnées et de la couleur via les setters
    @Test
    public void testSetters() {
        Element element = new Element(new Coordonnees(0, 0), Couleur.BLEU);
        Coordonnees nouvelleCoord = new Coordonnees(4, 5);
        element.setCoordonnees(nouvelleCoord);
        element.setCouleur(Couleur.VERT);

        assertEquals(nouvelleCoord, element.getCoordonnees());
        assertEquals(Couleur.VERT, element.getCouleur());
    }

    // Vérifie la représentation textuelle fournie par la méthode toString
    @Test
    public void testToString() {
        Element element = new Element(new Coordonnees(3, 3), Couleur.JAUNE);
        assertEquals("[JAUNE : (3, 3)]", element.toString());
    }

    // Vérifie l'égalité logique entre deux éléments identiques, ainsi que le hashCode
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