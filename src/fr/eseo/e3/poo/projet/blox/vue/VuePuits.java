package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.controleur.Clavier;
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

public class VuePuits extends JPanel implements PropertyChangeListener {

    public static final int TAILLE_PAR_DEFAUT = 20;

    private Puits puits;
    private int taille;
    private VuePiece vuePiece;
    private VueTas vueTas;
    private Gravite gravite;
    private PieceDeplacement pieceDeplacement;
    private PieceRotation pieceRotation;
    private Clavier clavier;

    public VuePuits(Puits puits) {
        this(puits, TAILLE_PAR_DEFAUT);
    }

    public VuePuits(Puits puits, int taille) {
        this.puits = puits;
        this.taille = taille;
        initialiser();
    }

    private void initialiser() {
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

        this.clavier = new Clavier(puits, this);
        this.addKeyListener(clavier);

        setFocusable(true);
        requestFocusInWindow();

        setPreferredSize(new Dimension(puits.getLargeur() * taille, puits.getHauteur() * taille));
        setBackground(Color.WHITE);
    }

    public void setGravite(Gravite gravite) {
        this.gravite = gravite;
    }

    public Gravite getGravite() {
        return gravite;
    }

    public Puits getPuits() {
        return puits;
    }

    public void setPuits(Puits puits) {
        if (this.puits != null) {
            this.puits.removePropertyChangeListener(this);
            this.removeMouseMotionListener(pieceDeplacement);
            this.removeMouseListener(pieceDeplacement);
            this.removeMouseWheelListener(pieceDeplacement);
            this.removeMouseListener(pieceRotation);
            this.removeKeyListener(clavier);
        }

        this.puits = puits;
        this.vuePiece = null;
        this.vueTas = new VueTas(puits, taille);

        this.pieceDeplacement = new PieceDeplacement(puits, this);
        this.pieceRotation = new PieceRotation(this);
        this.clavier = new Clavier(puits, this);

        this.puits.setVuePuits(this);
        this.puits.addPropertyChangeListener(this);

        this.addMouseMotionListener(pieceDeplacement);
        this.addMouseListener(pieceDeplacement);
        this.addMouseWheelListener(pieceDeplacement);
        this.addMouseListener(pieceRotation);
        this.addKeyListener(clavier);

        repaint();
        revalidate();
        requestFocusInWindow();
    }

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g.create();

        g2D.setColor(Color.WHITE);
        g2D.fillRect(0, 0, getWidth(), getHeight());

        g2D.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= puits.getLargeur(); i++) {
            int x = i * taille;
            g2D.drawLine(x, 0, x, puits.getHauteur() * taille);
        }
        for (int j = 0; j <= puits.getHauteur(); j++) {
            int y = j * taille;
            g2D.drawLine(0, y, puits.getLargeur() * taille, y);
        }

        if (vueTas != null) {
            vueTas.afficher(g2D);
        }

        if (vuePiece != null) {
            vuePiece.afficherPiece(g2D);
        }

        g2D.dispose();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Puits.MODIFICATION_PIECE_ACTUELLE.equals(evt.getPropertyName())) {
            Piece nouvellePiece = (Piece) evt.getNewValue();
            if (nouvellePiece != null) {
                this.setVuePiece(new VuePiece(nouvellePiece, this.taille));
            } else {
                this.setVuePiece(null);
            }
        } else if (Puits.FIN_PARTIE.equals(evt.getPropertyName())) {
            terminerPartie();
        }
    }

    private void terminerPartie() {
        if (gravite != null) {
            gravite.stop();
        }

        this.removeKeyListener(clavier);
        this.removeMouseListener(pieceDeplacement);
        this.removeMouseMotionListener(pieceDeplacement);
        this.removeMouseWheelListener(pieceDeplacement);
        this.removeMouseListener(pieceRotation);

        JOptionPane.showMessageDialog(this, "Fin de la partie", "Partie terminée", JOptionPane.INFORMATION_MESSAGE);
    }
    public void deconnectListeners() {
        this.removeMouseMotionListener(pieceDeplacement);
        this.removeMouseListener(pieceDeplacement);
        this.removeMouseWheelListener(pieceDeplacement);
        this.removeMouseListener(pieceRotation);
        this.removeKeyListener(clavier);
    }

    public void reconnectListeners() {
        this.addMouseMotionListener(pieceDeplacement);
        this.addMouseListener(pieceDeplacement);
        this.addMouseWheelListener(pieceDeplacement);
        this.addMouseListener(pieceRotation);
        this.addKeyListener(clavier);
        this.requestFocusInWindow();
    }
}