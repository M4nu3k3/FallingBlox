package fr.eseo.e3.poo.projet.blox.modele;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un puits de jeu dans lequel tombent les pièces.
 */
public class Puits {

    // Constantes de dimensions
    public static final int LARGEUR_PAR_DEFAUT = 10;
    public static final int PROFONDEUR_PAR_DEFAUT = 20;

    // Identifiants pour les événements de changement
    public static final String MODIFICATION_PIECE_ACTUELLE = "pieceActuelle";
    public static final String MODIFICATION_PIECE_SUIVANTE = "pieceSuivante";

    // Attributs principaux
    private int largeur;
    private int profondeur;
    private Piece pieceActuelle;
    private Piece pieceSuivante;
    private final List<Piece> pieces;
    private Tas tas;
    private VuePuits vuePuits;

    // Support d'écoute des événements
    private final PropertyChangeSupport pcs;

    /* === Constructeurs === */

    public Puits() {
        this(LARGEUR_PAR_DEFAUT, PROFONDEUR_PAR_DEFAUT);
    }

    public Puits(int largeur, int profondeur) {
        setLargeur(largeur);
        setProfondeur(profondeur);
        this.pieces = new ArrayList<>();
        this.tas = new Tas(this);
        this.pcs = new PropertyChangeSupport(this);
    }

    public Puits(int largeur, int profondeur, int nbElements, int nbLignes) {
        setLargeur(largeur);
        setProfondeur(profondeur);
        this.pieces = new ArrayList<>();
        this.tas = new Tas(this, nbElements, nbLignes);
        this.pcs = new PropertyChangeSupport(this);
    }

    /* === Accesseurs dimensions === */

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        if (largeur < 5 || largeur > 15) {
            throw new IllegalArgumentException("La largeur doit être comprise entre 5 et 15.");
        }
        this.largeur = largeur;
    }

    public int getProfondeur() {
        return profondeur;
    }

    public void setProfondeur(int profondeur) {
        if (profondeur < 10 || profondeur > 25) {
            throw new IllegalArgumentException("La profondeur doit être comprise entre 10 et 25.");
        }
        this.profondeur = profondeur;
    }

    public int getHauteur() {
        return getProfondeur();
    }

    public void setHauteur(int hauteur) {
        setProfondeur(hauteur);
    }

    /* === Vue associée === */

    public VuePuits getVuePuits() {
        return vuePuits;
    }

    public void setVuePuits(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
    }

    /* === Tas et gestion des pièces === */

    public Tas getTas() {
        return tas;
    }

    public void setTas(Tas tas) {
        this.tas = tas;
    }

    public Piece getPieceActuelle() {
        return pieceActuelle;
    }

    public Piece getPieceSuivante() {
        return pieceSuivante;
    }

    public void setPieceActuelle(Piece piece) {
        Piece ancienne = this.pieceActuelle;
        this.pieceActuelle = piece;

        if (piece != null) {
            piece.setPosition(largeur / 2, 0);
            piece.setPuits(this);
        }

        pcs.firePropertyChange(MODIFICATION_PIECE_ACTUELLE, ancienne, pieceActuelle);
    }

    public void setPieceSuivante(Piece piece) {
        Piece ancienne = this.pieceSuivante;

        if (this.pieceSuivante != null) {
            setPieceActuelle(this.pieceSuivante);
        } else if (this.pieceActuelle == null && piece != null) {
            setPieceActuelle(piece);
            piece = null;
        }

        this.pieceSuivante = piece;

        if (this.pieceSuivante != null) {
            this.pieceSuivante.setPosition(largeur / 2, 0);
        }

        pcs.firePropertyChange(MODIFICATION_PIECE_SUIVANTE, ancienne, pieceSuivante);
    }

    public void ajouterPiece(Piece piece) {
        if (piece != null) {
            this.pieces.add(piece);
            this.setPieceActuelle(piece);
        }
    }

    public List<Piece> getPieces() {
        return this.pieces;
    }

    /* === Listeners === */

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    /* === Gravité et collisions === */

    private void gererCollision() {
        tas.ajouterElements(pieceActuelle);
        setPieceActuelle(pieceSuivante);
        setPieceSuivante(UsineDePiece.genererPiece());
    }

    public void gravite() throws BloxException {
        if (pieceActuelle != null) {
            try {
                pieceActuelle.deplacerDe(0, 1);
            } catch (BloxException e) {
                gererCollision();
                throw e;
            }
        }
    }

    /* === Affichage texte (debug) === */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Puits : Dimension ").append(largeur).append(" x ").append(profondeur).append("\n");

        sb.append("Piece Actuelle : ");
        if (pieceActuelle == null) {
            sb.append("<aucune>\n");
        } else {
            sb.append(pieceActuelle.getClass().getSimpleName()).append("\n");
            for (Element e : pieceActuelle.getElements()) {
                sb.append("    ").append(e).append("\n");
            }
        }

        sb.append("Piece Suivante : ");
        if (pieceSuivante == null) {
            sb.append("<aucune>\n");
        } else {
            sb.append(pieceSuivante.getClass().getSimpleName()).append("\n");
            for (Element e : pieceSuivante.getElements()) {
                sb.append("    ").append(e).append("\n");
            }
        }

        return sb.toString().trim();
    }
}