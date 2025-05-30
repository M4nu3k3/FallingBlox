package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Panneau d'information affichant la prochaine pièce à jouer.
 */
public class PanneauInformation extends JPanel implements PropertyChangeListener {

    public static final int TAILLE_PAR_DEFAUT = 70;

    private VuePiece vuePiece;

    /**
     * Crée un panneau d'information lié au puits.
     */
    public PanneauInformation(Puits puits) {
        puits.addPropertyChangeListener(this);
        this.setPreferredSize(new Dimension(TAILLE_PAR_DEFAUT, TAILLE_PAR_DEFAUT));
    }

    /**
     * Écoute les changements de pièce suivante dans le puits.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Puits.MODIFICATION_PIECE_SUIVANTE.equals(evt.getPropertyName())) {
            Piece nouvellePiece = (Piece) evt.getNewValue();
            this.vuePiece = new VuePiece(nouvellePiece, TAILLE_PAR_DEFAUT / 3); // Ajustement pour la taille du panneau
            repaint();
        }
    }

    /**
     * Dessine le panneau d'information.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (vuePiece != null) {
            Graphics2D g2D = (Graphics2D) g.create();
            g2D.translate(10, 10); // Décalage pour marges visuelles
            vuePiece.afficherPiece(g2D);
            g2D.dispose();
        }
    }
}