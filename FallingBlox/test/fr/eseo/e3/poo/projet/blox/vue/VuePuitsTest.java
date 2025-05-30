package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests unitaires pour la classe VuePuits.
 */
public class VuePuitsTest {

    /**
     * Vérifie que le constructeur avec un seul paramètre associe correctement le puits
     * et utilise la taille par défaut.
     */
    @Test
    public void testConstructeurAvecUnParametre() {
        Puits puits = new Puits(10, 20);
        VuePuits vue = new VuePuits(puits);

        assertEquals(puits, vue.getPuits(), "Le puits associé doit être celui passé en paramètre.");
        assertEquals(VuePuits.TAILLE_PAR_DEFAUT, vue.getTaille(), "La taille doit être la taille par défaut.");
    }

    /**
     * Vérifie que le constructeur à deux paramètres affecte correctement le puits et la taille.
     */
    @Test
    public void testConstructeurAvecDeuxParametres() {
        Puits puits = new Puits(8, 15);
        int taille = 25;
        VuePuits vue = new VuePuits(puits, taille);

        assertEquals(puits, vue.getPuits(), "Le puits associé doit être celui passé en paramètre.");
        assertEquals(taille, vue.getTaille(), "La taille doit être celle fournie en paramètre.");
    }

    /**
     * Vérifie que le setter de taille modifie correctement l'attribut correspondant.
     */
    @Test
    public void testSetTaille() {
        Puits puits = new Puits();
        VuePuits vue = new VuePuits(puits);
        vue.setTaille(40);

        assertEquals(40, vue.getTaille(), "La taille doit être mise à jour à la nouvelle valeur.");
    }

    /**
     * Vérifie que le setter de puits met bien à jour l'objet associé.
     */
    @Test
    public void testSetPuits() {
        Puits puits1 = new Puits();
        VuePuits vue = new VuePuits(puits1);

        Puits puits2 = new Puits(12, 22);
        vue.setPuits(puits2);

        assertEquals(puits2, vue.getPuits(), "Le nouveau puits doit être correctement mis à jour.");
    }
}