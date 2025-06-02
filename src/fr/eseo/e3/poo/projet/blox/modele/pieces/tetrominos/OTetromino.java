package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;

import java.util.ArrayList;
import java.util.List;

/**
 * Représentation de la pièce OTetromino (carré 2x2).
 * Cette pièce est symétrique et ne tourne jamais.
 */
public class OTetromino extends Tetromino {

    /**
     * Constructeur par défaut : pivot à (4, 0), couleur rouge.
     */
    public OTetromino() {
        super(new Coordonnees(4, 0), Couleur.ROUGE);
    }

    /**
     * Constructeur avec couleur personnalisée, pivot fixé à (4, 0).
     *
     * @param couleur la couleur de la pièce
     */
    public OTetromino(Couleur couleur) {
        super(new Coordonnees(4, 0), couleur);
    }

    /**
     * Constructeur complet avec coordonnées de pivot et couleur.
     *
     * @param coordonnees les coordonnées du pivot
     * @param couleur la couleur de la pièce
     */
    public OTetromino(Coordonnees coordonnees, Couleur couleur) {
        super(coordonnees, couleur);
    }

    /**
     * Définit la forme carrée autour du pivot (2x2).
     *
     * @param abscisse abscisse du pivot
     * @param ordonnee ordonnée du pivot
     * @return la liste des coordonnées relatives formant la pièce
     */
    @Override
    protected List<Coordonnees> getFormeRelative(int abscisse, int ordonnee) {
        List<Coordonnees> coords = new ArrayList<>();
        coords.add(new Coordonnees(abscisse, ordonnee));         // coin haut gauche
        coords.add(new Coordonnees(abscisse + 1, ordonnee));     // coin haut droit
        coords.add(new Coordonnees(abscisse, ordonnee + 1));     // coin bas gauche
        coords.add(new Coordonnees(abscisse + 1, ordonnee + 1)); // coin bas droit
        return coords;
    }

    /**
     * Override vide car la pièce O est symétrique et ne peut pas tourner.
     *
     * @param sensHoraire ignoré
     */
    @Override
    public void tourner(boolean sensHoraire) {
        // Rotation désactivée pour la pièce O
    }

    /**
     * Affiche la représentation textuelle de la pièce pour débogage.
     *
     * @return chaîne décrivant les éléments de la pièce
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("OTetromino :");
        for (var e : getElements()) {
            sb.append("\n\t").append(e.toString());
        }
        return sb.toString();
    }
}