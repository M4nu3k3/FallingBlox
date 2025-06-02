package fr.eseo.e3.poo.projet.blox.modele;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test unitaire pour la classe BloxException
 */
public class BloxExceptionTest {

    /**
     * Vérifie que le message est correctement transmis au constructeur
     */
    @Test
    public void testMessageEtCode() {
        String message = "Erreur de collision détectée";
        int code = BloxException.BLOX_COLLISION;

        BloxException exception = new BloxException(message, code);

        assertEquals(message, exception.getMessage(), "Le message doit être conservé.");
        assertEquals(code, exception.getCode(), "Le code d’erreur doit correspondre.");
    }

    /**
     * Vérifie la constante BLOX_SORTIE_PUITS
     */
    @Test
    public void testCodeSortiePuits() {
        BloxException exception = new BloxException("Sortie du puits", BloxException.BLOX_SORTIE_PUITS);
        assertEquals(BloxException.BLOX_SORTIE_PUITS, exception.getCode(), "Le code de sortie du puits doit être correct.");
    }
}