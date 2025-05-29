package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.controleur.Gravite;
import fr.eseo.e3.poo.projet.blox.controleur.PieceDeplacement;
import fr.eseo.e3.poo.projet.blox.controleur.PieceRotation;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Composant Swing représentant graphiquement un puits de jeu Blox,
 * avec sa grille, le tas, et la pièce actuelle.
 */
public class VuePuits extends JPanel implements PropertyChangeListener {

    public static final int TAILLE_PAR_DEFAUT = 20;

    private Puits puits;
    private int taille;
    private VuePiece vuePiece;
    private VueTas vueTas;
    private Gravite gravite;
    private PieceDeplacement pieceDeplacement;
    private PieceRotation pieceRotation;

    /* === Constructeurs === */

    public VuePuits(Puits puits) {
        this(puits, TAILLE_PAR_DEFAUT);
    }

    public VuePuits(Puits puits, int taille) {
        this.puits = puits;
        this.taille = taille;

        this.vuePiece = null;
        this.vueTas = new VueTas(puits, taille);

        puits.setVuePuits(this);
        puits.addPropertyChangeListener(this);

        this.pieceDeplacement = new PieceDeplacement(puits, this);
        this.pieceRotation = new PieceRotation(this);

        this.addMouseMotionListener(pieceDeplacement);
        this.addMouseListener(pieceDeplacement);
        this.addMouseWheelListener(pieceDeplacement);
        this.addMouseListener(pieceRotation);

        setPreferredSize(new Dimension(puits.getLargeur() * taille, puits.getHauteur() * taille));
        setBackground(Color.WHITE);
        setFocusable(true);
        requestFocusInWindow();
    }

    /* === Gravité automatique === */

    public void demarrerGravite(int periode) {
        if (this.gravite == null) {
            this.gravite = new Gravite(this, periode);
        }
    }

    public Gravite getGravite() {
        return gravite;
    }

    /* === Accesseurs et mise à jour du puits === */

    public Puits getPuits() {
        return puits;
    }

    public void setPuits(Puits puits) {
        // Retirer les anciens écouteurs
        if (this.puits != null) {
            this.puits.removePropertyChangeListener(this);
            this.removeMouseMotionListener(pieceDeplacement);
            this.removeMouseListener(pieceDeplacement);
            this.removeMouseWheelListener(pieceDeplacement);
            this.removeMouseListener(pieceRotation);
        }

        // Associer le nouveau puits
        this.puits = puits;
        this.puits.setVuePuits(this);
        this.puits.addPropertyChangeListener(this);

        // Reconfigurer les vues et écouteurs
        this.vueTas = new VueTas(puits, taille);
        this.pieceDeplacement = new PieceDeplacement(puits, this);
        this.pieceRotation = new PieceRotation(this);

        this.addMouseMotionListener(pieceDeplacement);
        this.addMouseListener(pieceDeplacement);
        this.addMouseWheelListener(pieceDeplacement);
        this.addMouseListener(pieceRotation);

        repaint();
    }

    /* === Taille des cases === */

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
        setPreferredSize(new Dimension(puits.getLargeur() * taille, puits.getHauteur() * taille));
        if (vueTas != null) {
            vueTas.setTaille(taille);
        }
        revalidate();
        repaint();
    }

    /* === Vues internes === */

    public void setVuePiece(VuePiece vuePiece) {
        this.vuePiece = vuePiece;
        repaint();
    }

    public VuePiece getVuePiece() {
        return vuePiece;
    }

    public VueTas getVueTas() {
        return vueTas;
    }

    /* === Dessin du puits === */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g.create();

        // Fond
        g2D.setColor(Color.WHITE);
        g2D.fillRect(0, 0, getWidth(), getHeight());

        // Grille
        g2D.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= puits.getLargeur(); i++) {
            int x = i * taille;
            g2D.drawLine(x, 0, x, puits.getHauteur() * taille);
        }
        for (int j = 0; j <= puits.getHauteur(); j++) {
            int y = j * taille;
            g2D.drawLine(0, y, puits.getLargeur() * taille, y);
        }

        // Tas
        if (vueTas != null) {
            vueTas.afficher(g2D);
        }

        // Pièce en cours
        if (vuePiece != null) {
            vuePiece.afficherPiece(g2D);
        }

        g2D.dispose();
    }

    /* === Réaction aux changements dans le puits === */

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Puits.MODIFICATION_PIECE_ACTUELLE.equals(evt.getPropertyName())) {
            Piece nouvellePiece = (Piece) evt.getNewValue();
            this.setVuePiece(new VuePiece(nouvellePiece, this.taille));
        }
    }
}