package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import java.awt.*;

/**
 * Classe qui dessine chaque élément avec sa couleur et indique le pivot par un cercle
 */
public class VuePiece {

    public static final double MULTIPLIER_TEINTE = 0.3;

    private final Piece piece;
    private final int taille;

    /**
     * Crée une vue graphique pour une pièce avec une taille de case spécifiée
     *
     * @param piece la pièce à afficher
     * @param taille la taille en pixels d'une case (élément)
     */
    public VuePiece(Piece piece, int taille) {
        this.piece = piece;
        this.taille = taille;
    }

    /**
     * Renvoie une version éclaircie (teintée vers le blanc) de la couleur donnée
     * Utile pour mettre en valeur le pivot
     *
     * @param couleur couleur originale
     * @return couleur éclaircie
     */
    public static Color teinte(Color couleur) {
        int r = couleur.getRed();
        int g = couleur.getGreen();
        int b = couleur.getBlue();

        r = (int) (r + (255 - r) * MULTIPLIER_TEINTE);
        g = (int) (g + (255 - g) * MULTIPLIER_TEINTE);
        b = (int) (b + (255 - b) * MULTIPLIER_TEINTE);

        return new Color(r, g, b);
    }

    /**
     * Dessine la pièce
     * Le pivot est mis en valeur par un cercle semi-transparent.
     *
     * @param g2D contexte graphique 2D
     */
    public void afficherPiece(Graphics2D g2D) {
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < piece.getElements().size(); i++) {
            Element e = piece.getElements().get(i);
            int x = e.getCoordonnees().getAbscisse() * taille;
            int y = e.getCoordonnees().getOrdonnee() * taille;

            Couleur couleur = e.getCouleur();
            Color color = couleur.getCouleurPourAffichage();

            // Appliquer une teinte plus claire uniquement sur le pivot (index 1)
            g2D.setColor(i == 1 ? teinte(color) : color);
            g2D.fill3DRect(x, y, taille, taille, true);

            // Dessiner la bordure de l’élément
            g2D.setColor(Color.DARK_GRAY);
            g2D.drawRect(x, y, taille, taille);

            // Afficher un cercle indiquant le pivot
            if (i == 1) {
                int circleSize = taille / 2;
                int offset = (taille - circleSize) / 2;

                g2D.setColor(new Color(255, 255, 255, 128));  // Cercle semi-transparent
                g2D.fillOval(x + offset, y + offset, circleSize, circleSize);

                g2D.setColor(Color.GRAY);
                g2D.setStroke(new BasicStroke(2));
                g2D.drawOval(x + offset, y + offset, circleSize, circleSize);

                // Restauration du trait par défaut
                g2D.setStroke(new BasicStroke(1));
            }
        }
    }
}