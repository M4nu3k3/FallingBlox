package fr.eseo.e3.poo.projet.blox.modele;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un puits de jeu contenant les pièces tombantes, le tas de blocs posés,
 * la logique de score, de collision et de progression de la partie.
 */
public class Puits {

    // Dimensions par défaut du puits
    public static final int LARGEUR_PAR_DEFAUT = 10;
    public static final int PROFONDEUR_PAR_DEFAUT = 20;

    // Constantes de notification pour les PropertyChangeListener
    public static final String MODIFICATION_PIECE_ACTUELLE = "pieceActuelle";
    public static final String MODIFICATION_PIECE_SUIVANTE = "pieceSuivante";
    public static final String FIN_PARTIE = "finPartie";

    // Attributs principaux du puits
    private int largeur;
    private int profondeur;
    private Piece pieceActuelle;
    private Piece pieceSuivante;
    private final List<Piece> pieces;
    private Tas tas;
    private VuePuits vuePuits;
    private boolean partieTerminee = false;

    // Gestion du score
    private int score = 0;
    private int meilleurScore = 0;

    // Support d'écoute pour notifier les vues
    private final PropertyChangeSupport pcs;

    // Constructeurs
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

    // Accesseurs et mutateurs (dimensions)
    public int getLargeur() { return largeur; }

    public void setLargeur(int largeur) {
        if (largeur < 5 || largeur > 15)
            throw new IllegalArgumentException("La largeur doit être comprise entre 5 et 15.");
        this.largeur = largeur;
    }

    public int getProfondeur() { return profondeur; }

    public void setProfondeur(int profondeur) {
        if (profondeur < 10 || profondeur > 25)
            throw new IllegalArgumentException("La profondeur doit être comprise entre 10 et 25.");
        this.profondeur = profondeur;
    }

    public int getHauteur() { return getProfondeur(); }

    public void setHauteur(int hauteur) { setProfondeur(hauteur); }

    // Vue liée au puits
    public VuePuits getVuePuits() { return vuePuits; }

    public void setVuePuits(VuePuits vuePuits) { this.vuePuits = vuePuits; }

    // Tas de blocs posés
    public Tas getTas() { return tas; }

    public void setTas(Tas tas) { this.tas = tas; }

    // Gestion des pièces
    public Piece getPieceActuelle() { return pieceActuelle; }

    public Piece getPieceSuivante() { return pieceSuivante; }

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

    // PropertyChangeListener
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void firePropertyChange(String nom, int ancien, int nouveau) {
        pcs.firePropertyChange(nom, ancien, nouveau);
    }

    public void firePropertyChange(String nom, boolean ancien, boolean nouveau) {
        pcs.firePropertyChange(nom, ancien, nouveau);
    }

    // Gestion du score
    public int getScore() { return score; }

    public int getMeilleurScore() { return meilleurScore; }

    public void resetScore() {
        int ancien = this.score;
        this.score = 0;
        pcs.firePropertyChange("score", ancien, this.score);
    }

    public void incrementerScorePourTest(int valeur) {
        int ancien = this.score;
        this.score += valeur;
        if (this.score > this.meilleurScore)
            this.meilleurScore = this.score;
        pcs.firePropertyChange("score", ancien, this.score);
    }

    // Gravité : fait descendre la pièce actuelle
    public void gravite() throws BloxException {
        if (partieTerminee) return;

        if (pieceActuelle != null) {
            try {
                pieceActuelle.deplacerDe(0, 1);
            } catch (BloxException e) {
                gererCollision();
                throw e;
            }
        }
    }

    // Gère la fin de déplacement d'une pièce et la transition
    private void gererCollision() {
        tas.ajouterElements(pieceActuelle);
        int lignesSupprimees = tas.detecterEtSupprimerLignesComplete();

        if (lignesSupprimees > 0) {
            int ancienScore = this.score;
            this.score += lignesSupprimees;
            pcs.firePropertyChange("score", ancienScore, this.score);
            if (this.score > this.meilleurScore)
                this.meilleurScore = this.score;
        }

        if (!partieTerminee) {
            if (pieceSuivante == null)
                setPieceActuelle(UsineDePiece.genererPiece());
            else {
                setPieceActuelle(pieceSuivante);
                setPieceSuivante(UsineDePiece.genererPiece());
            }

            if (tas.contient(pieceActuelle))
                setPartieTerminee(true);
        }
    }

    // Fin de partie
    public boolean isPartieTerminee() { return this.partieTerminee; }

    public void setPartieTerminee(boolean terminee) {
        boolean ancien = this.partieTerminee;
        this.partieTerminee = terminee;
        pcs.firePropertyChange(FIN_PARTIE, ancien, terminee);
    }

    // Réinitialisation complète du puits
    public void reset() {
        this.pieceActuelle = null;
        this.pieceSuivante = null;
        this.pieces.clear();
        this.tas = new Tas(this);
        setPartieTerminee(false);
        resetScore();
        setPieceActuelle(UsineDePiece.genererPiece());
        setPieceSuivante(UsineDePiece.genererPiece());
    }

    /**
     * Calcule une copie de la pièce actuelle à sa position de chute maximale
     * sans collision dans le tas ou sortie du puits.
     *
     * @return une nouvelle pièce représentant la position de chute
     */
    public Piece calculerPieceFantome() {
        if (this.pieceActuelle == null)
            return null;

        Piece fantome = this.pieceActuelle.dupliquer();
        fantome.setPuits(this); // pour que la méthode de collision fonctionne

        while (true) {
            try {
                fantome.deplacerDe(0, 1);
            } catch (BloxException e) {
                break;
            }
        }

        return fantome;
    }

    // Affichage du contenu du puits
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Puits : Dimension ").append(largeur).append(" x ").append(profondeur).append("\n");

        sb.append("Piece Actuelle : ");
        if (pieceActuelle == null)
            sb.append("<aucune>\n");
        else {
            sb.append(pieceActuelle.getClass().getSimpleName()).append("\n");
            for (Element e : pieceActuelle.getElements()) {
                sb.append("    ").append(e).append("\n");
            }
        }

        sb.append("Piece Suivante : ");
        if (pieceSuivante == null)
            sb.append("<aucune>\n");
        else {
            sb.append(pieceSuivante.getClass().getSimpleName()).append("\n");
            for (Element e : pieceSuivante.getElements()) {
                sb.append("    ").append(e).append("\n");
            }
        }

        return sb.toString().trim();
    }
}