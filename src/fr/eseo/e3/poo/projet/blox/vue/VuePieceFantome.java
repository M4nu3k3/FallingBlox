package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import java.awt.*;

/**
 * Classe qui dessine la pièce fantôme (ghost piece) en gris translucide à la position de chute.
 */
public class VuePieceFantome extends VuePiece {

    /**
     * Crée une vue graphique pour la pièce fantôme.
     *
     * @param piece la pièce fantôme à afficher
     * @param taille la taille des cases en pixels
     */
    public VuePieceFantome(Piece piece, int taille) {
        super(piece, taille);
    }

    /**
     * Affiche la pièce fantôme en utilisant une couleur semi-transparente.
     *
     * @param g2D contexte graphique 2D
     */
    @Override
    public void afficherPiece(Graphics2D g2D) {
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Element e : this.piece.getElements()) {
            int x = e.getCoordonnees().getAbscisse() * this.taille;
            int y = e.getCoordonnees().getOrdonnee() * this.taille;

            g2D.setColor(new Color(100, 100, 100, 80)); // gris semi-transparent
            g2D.fillRect(x, y, this.taille, this.taille);

            g2D.setColor(Color.DARK_GRAY);
            g2D.drawRect(x, y, this.taille, this.taille);
        }
    }
}