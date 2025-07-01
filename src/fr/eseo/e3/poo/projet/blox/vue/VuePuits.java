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
 * Composant graphique principal représentant le puits de jeu
 * Il gère l'affichage des pièces, du tas, de la pièce fantôme,
 * et les interactions clavier/souris avec l'utilisateur
 */
public class VuePuits extends JPanel implements PropertyChangeListener {

    public static final int TAILLE_PAR_DEFAUT = 20;

    private Puits puits;
    private int taille;

    private VuePiece vuePiece;
    private VuePieceFantome vueFantome;
    private VueTas vueTas;

    private Gravite gravite;
    private PieceDeplacement pieceDeplacement;
    private PieceRotation pieceRotation;
    private Clavier clavier;

    //  Constructeurs taille par défaut
    public VuePuits(Puits puits) {
        this(puits, TAILLE_PAR_DEFAUT);
    }

    /**
     * Crée une VuePuits avec une taille de case personnalisée
     *
     * @param puits le puits de jeu associé
     * @param taille la taille des cases
     */
    public VuePuits(Puits puits, int taille) {
        this.puits = puits;
        this.taille = taille;
        initialiser();
    }

    // === Initialisation ===

    /**
     * Initialise les contrôleurs, les écouteurs et les vues*
     */
    private void initialiser() {
        this.vuePiece = null;
        this.vueFantome = null;
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

    // getter et setter

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
     * Change le puits associé à cette vue
     *
     * @param puits le nouveau puits
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
        this.vueFantome = null;
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
     * Modifie la taille d'affichage des cases
     *
     * @param taille la nouvelle taille
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

    public VuePiece getVuePiece() {
        return vuePiece;
    }

    public void setVuePiece(VuePiece vuePiece) {
        this.vuePiece = vuePiece;
        repaint();
    }

    public VueTas getVueTas() {
        return vueTas;
    }

    // Affichage de la pièce fantôme

    /**
     * Calcule et met à jour la position de la pièce fantôme
     * (position finale de chute)
     */
    public void mettreAJourFantome() {
        if (puits.getPieceActuelle() != null) {
            Piece fantome = puits.getPieceActuelle().dupliquer();
            fantome.setPuits(puits);
            while (true) {
                try {
                    fantome.deplacerDe(0, 1);
                } catch (Exception e) {
                    break;
                }
            }
            this.vueFantome = new VuePieceFantome(fantome, taille);
        } else {
            this.vueFantome = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        mettreAJourFantome();
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
        if (vueFantome != null) {
            vueFantome.afficherPiece(g2D);
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

    /**
     * Actions à effectuer en fin de partie
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

    // === Utilitaires pour réinitialisation ou pause ===

    /**
     * Supprime temporairement tous les listenner de cette vue
     */
    public void deconnectListeners() {
        this.removeMouseMotionListener(pieceDeplacement);
        this.removeMouseListener(pieceDeplacement);
        this.removeMouseWheelListener(pieceDeplacement);
        this.removeMouseListener(pieceRotation);
        this.removeKeyListener(clavier);
    }

    /**
     * Réactive tous les listenner précédemment associés à la vue
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