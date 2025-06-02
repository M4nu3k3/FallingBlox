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

/**
 * Vue principale du puits
 * Gère l'affichage du puits, de la pièce actuelle, du tas et les interactions utilisateur
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
    private Clavier clavier;

    /**
     * Constructeur par défaut avec taille par défaut des cases
     *
     * @param puits modèle du puits de jeu
     */
    public VuePuits(Puits puits) {
        this(puits, TAILLE_PAR_DEFAUT);
    }

    /**
     * Constructeur avec taille personnalisée des cases
     *
     * @param puits modèle du puits de jeu
     * @param taille taille en pixels d’une case
     */
    public VuePuits(Puits puits, int taille) {
        this.puits = puits;
        this.taille = taille;
        initialiser();
    }

    /**
     * Initialisation des composants graphiques et écouteurs
     */
    private void initialiser() {
        this.vuePiece = null;
        this.vueTas = new VueTas(puits, taille);

        // Liaison modèle-vue
        puits.setVuePuits(this);
        puits.addPropertyChangeListener(this);

        // Création des contrôleurs de la vue
        this.pieceDeplacement = new PieceDeplacement(puits, this);
        this.pieceRotation = new PieceRotation(this);

        // Ajout des écouteurs souris
        this.addMouseMotionListener(pieceDeplacement);
        this.addMouseListener(pieceDeplacement);
        this.addMouseWheelListener(pieceDeplacement);
        this.addMouseListener(pieceRotation);

        // Ajout écouteur clavier
        this.clavier = new Clavier(puits, this);
        this.addKeyListener(clavier);

        setFocusable(true);
        requestFocusInWindow();

        // Taille préférée du composant
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

    /**
     * Change le modèle du puits associé à la vue
     * Retire les anciens écouteurs et en ajoute de nouveaux pour le nouveau modèle
     *
     * @param puits nouveau modèle du puits
     */
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

    /**
     * Change la taille des cases, ajuste la taille préférée, met à jour la vue du tas
     *
     * @param taille nouvelle taille des cases en pixels
     */
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

    /**
     * Dessine le fond, la grille, le tas et la pièce actuelle
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g.create();

        // Fond blanc
        g2D.setColor(Color.WHITE);
        g2D.fillRect(0, 0, getWidth(), getHeight());

        // Grille du puits
        g2D.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= puits.getLargeur(); i++) {
            int x = i * taille;
            g2D.drawLine(x, 0, x, puits.getHauteur() * taille);
        }
        for (int j = 0; j <= puits.getHauteur(); j++) {
            int y = j * taille;
            g2D.drawLine(0, y, puits.getLargeur() * taille, y);
        }

        // Affichage du tas et de la pièce
        if (vueTas != null) {
            vueTas.afficher(g2D);
        }
        if (vuePiece != null) {
            vuePiece.afficherPiece(g2D);
        }

        g2D.dispose();
    }

    /**
     * Réagit aux changements du modèle
     *
     * @param evt événement de changement
     */
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

    /**
     * Termine la partie en stoppant la gravité et en désactivant les contrôles
     * Affiche un message de fin
     */
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

    /**
     * Déconnecte tous les écouteurs liés aux interactions utilisateur
     */
    public void deconnectListeners() {
        this.removeMouseMotionListener(pieceDeplacement);
        this.removeMouseListener(pieceDeplacement);
        this.removeMouseWheelListener(pieceDeplacement);
        this.removeMouseListener(pieceRotation);
        this.removeKeyListener(clavier);
    }

    /**
     * Reconnecte tous les écouteurs liés aux interactions utilisateur
     */
    public void reconnectListeners() {
        this.addMouseMotionListener(pieceDeplacement);
        this.addMouseListener(pieceDeplacement);
        this.addMouseWheelListener(pieceDeplacement);
        this.addMouseListener(pieceRotation);
        this.addKeyListener(clavier);
        this.requestFocusInWindow();
    }
}
