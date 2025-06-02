package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;

import java.util.ArrayList;
import java.util.List;

/**
 * Représentation de la pièce ZTetromino
 * Rotation centrée autour du pivot
 */
public class ZTetromino extends Tetromino {

    /**
     * Constructeur avec couleur personnalisée
     * Le pivot est initialisé à la position (4, 1)
     *
     * @param couleur la couleur de la pièce
     */
    public ZTetromino(Couleur couleur) {
        super(new Coordonnees(4, 1), couleur);
    }

    /**
     * Détermine les coordonnées relatives des blocs de la pièce Z, centrée autour du pivot spécifié
     *
     * @param abscisse abscisse du pivot
     * @param ordonnee ordonnée du pivot
     * @return la liste des coordonnées formant la pièce
     */
    @Override
    protected List<Coordonnees> getFormeRelative(int abscisse, int ordonnee) {
        List<Coordonnees> coords = new ArrayList<>();
        coords.add(new Coordonnees(abscisse - 1, ordonnee));     // bloc en haut à gauche
        coords.add(new Coordonnees(abscisse, ordonnee));         // pivot (centre)
        coords.add(new Coordonnees(abscisse, ordonnee + 1));     // bloc en bas centre
        coords.add(new Coordonnees(abscisse + 1, ordonnee + 1)); // bloc en bas à droite
        return coords;
    }
}