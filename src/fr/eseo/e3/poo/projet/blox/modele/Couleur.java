package fr.eseo.e3.poo.projet.blox.modele;

import java.awt.Color;

/**
 * Énumération représentant les couleurs possibles des pièces,
 * avec leur équivalent graphique (java.awt.Color).
 */
public enum Couleur {

    ROUGE(Color.RED),
    ORANGE(Color.ORANGE),
    BLEU(Color.BLUE),
    VERT(Color.GREEN),
    JAUNE(Color.YELLOW),
    CYAN(Color.CYAN),
    VIOLET(Color.MAGENTA); // MAGENTA ≈ VIOLET

    private final Color couleurPourAffichage;

    /**
     * Associe chaque valeur de l’énumération à une couleur graphique.
     */
    private Couleur(Color couleurPourAffichage) {
        this.couleurPourAffichage = couleurPourAffichage;
    }

    /**
     * Retourne la couleur graphique utilisée pour l'affichage.
     */
    public Color getCouleurPourAffichage() {
        return this.couleurPourAffichage;
    }
}