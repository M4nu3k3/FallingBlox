package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;

import java.util.ArrayList;
import java.util.List;

/**
 * Représentation de la pièce JTetromino
 */
public class JTetromino extends Tetromino {

    /**
     * Constructeur par défaut : pivot positionné à (4, 1)
     *
     * @param couleur la couleur de la pièce
     */
    public JTetromino(Couleur couleur) {
        super(new Coordonnees(4, 1), couleur);
    }

    /**
     * Constructeur avec coordonnées de pivot personnalisées
     *
     * @param coordonnees position du pivot
     * @param couleur couleur de la pièce
     */
    public JTetromino(Coordonnees coordonnees, Couleur couleur) {
        super(coordonnees, couleur);
    }

    /**
     * Détermine les coordonnées relatives des blocs de la pièce J, centrée autour du pivot fourni
     *
     * @param abscisse abscisse du pivot
     * @param ordonnee ordonnée du pivot
     * @return liste des coordonnées des 4 blocs constituant la pièce
     */
    @Override
    protected List<Coordonnees> getFormeRelative(int abscisse, int ordonnee) {
        List<Coordonnees> coords = new ArrayList<>();
        coords.add(new Coordonnees(abscisse - 1, ordonnee));     // bloc à gauche du pivot
        coords.add(new Coordonnees(abscisse, ordonnee));         // pivot
        coords.add(new Coordonnees(abscisse + 1, ordonnee));     // bloc à droite du pivot
        coords.add(new Coordonnees(abscisse - 1, ordonnee + 1)); // bloc en dessous à gauche
        return coords;
    }
}