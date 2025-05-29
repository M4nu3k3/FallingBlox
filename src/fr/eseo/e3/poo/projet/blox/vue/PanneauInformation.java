package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PanneauInformation extends JPanel implements PropertyChangeListener {

    public static final int TAILLE_PAR_DEFAUT = 70;

    private VuePiece vuePiece;

    public PanneauInformation(Puits puits) {
        puits.addPropertyChangeListener(this);
        this.setPreferredSize(new Dimension(TAILLE_PAR_DEFAUT, TAILLE_PAR_DEFAUT));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Puits.MODIFICATION_PIECE_SUIVANTE.equals(evt.getPropertyName())) {
            Piece nouvellePiece = (Piece) evt.getNewValue();
            this.vuePiece = new VuePiece(nouvellePiece, TAILLE_PAR_DEFAUT / 3); // ajustement pour visibilité
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (vuePiece != null) {
            Graphics2D g2D = (Graphics2D) g.create();
            g2D.translate(10, 10); // petit décalage pour ne pas coller aux bords
            vuePiece.afficherPiece(g2D);
            g2D.dispose();
        }
    }
}