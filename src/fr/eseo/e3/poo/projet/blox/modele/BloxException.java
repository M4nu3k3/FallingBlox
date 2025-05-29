package fr.eseo.e3.poo.projet.blox.modele;

/**
 * Exception personnalisée pour les erreurs liées au jeu Blox.
 * Elle permet de différencier les types d'erreurs via un code.
 */
public class BloxException extends Exception {

    // Constantes représentant les types d'erreurs possibles
    public static final int BLOX_COLLISION = 1;
    public static final int BLOX_SORTIE_PUITS = 2;

    private final int code;

    /**
     * Construit une exception avec un message et un code d'erreur.
     *
     * @param message message d'erreur
     * @param code    code de l'erreur (BLOX_COLLISION ou BLOX_SORTIE_PUITS)
     */
    public BloxException(String message, int code) {
        super(message);
        this.code = code;
    }

    /**
     * Retourne le code d'erreur associé à cette exception.
     */
    public int getCode() {
        return code;
    }
}