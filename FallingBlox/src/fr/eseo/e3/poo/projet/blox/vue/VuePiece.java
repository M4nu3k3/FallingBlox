package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import java.awt.*;

/**
 * Classe responsable de l'affichage graphique d'une pièce du jeu.
 */
public class VuePiece {

    public static final double MULTIPLIER_TEINTE = 0.3;

    private final Piece piece;
    private final int taille;

    /**
     * Crée une vue pour une pièce avec une taille de case donnée.
     */
    public VuePiece(Piece piece, int taille) {
        this.piece = piece;
        this.taille = taille;
    }

    /**
     * Éclaircit une couleur en la teintant vers le blanc.
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
     * Affiche la pièce à l’aide du contexte graphique fourni.
     */
    public void afficherPiece(Graphics2D g2D) {
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < piece.getElements().size(); i++) {
            Element e = piece.getElements().get(i);
            int x = e.getCoordonnees().getAbscisse() * taille;
            int y = e.getCoordonnees().getOrdonnee() * taille;

            Couleur couleur = e.getCouleur();
            Color color = couleur.getCouleurPourAffichage();

            // Appliquer une teinte plus claire sur le pivot
            g2D.setColor(i == 1 ? teinte(color) : color);
            g2D.fill3DRect(x, y, taille, taille, true);

            // Bordure de l’élément
            g2D.setColor(Color.DARK_GRAY);
            g2D.drawRect(x, y, taille, taille);

            // Indicateur de pivot (2e élément)
            if (i == 1) {
                int circleSize = taille / 2;
                int offset = (taille - circleSize) / 2;

                // Cercle central semi-transparent
                g2D.setColor(new Color(255, 255, 255, 128));
                g2D.fillOval(x + offset, y + offset, circleSize, circleSize);

                // Contour du cercle
                g2D.setColor(Color.GRAY);
                g2D.setStroke(new BasicStroke(2));
                g2D.drawOval(x + offset, y + offset, circleSize, circleSize);

                // Restauration du trait par défaut
                g2D.setStroke(new BasicStroke(1));
            }
        }
    }
}