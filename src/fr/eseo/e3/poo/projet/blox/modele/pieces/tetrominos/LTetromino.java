package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;

import java.util.ArrayList;
import java.util.List;

/**
 * Représentation de la pièce LTetromino
 */
public class LTetromino extends Tetromino {

    /**
     * Constructeur par défaut : pivot positionné à (4, 1)
     */
    public LTetromino(Couleur couleur) {
        super(new Coordonnees(4, 1), couleur);
    }

    /**
     * Constructeur avec coordonnées de pivot personnalisées
     */
    public LTetromino(Coordonnees coordonnees, Couleur couleur) {
        super(coordonnees, couleur);
    }

    /**
     * Détermine les coordonnées relatives des blocs de la pièce L, centrée autour du pivot fourni
     */
    @Override
    protected List<Coordonnees> getFormeRelative(int abscisse, int ordonnee) {
        List<Coordonnees> coords = new ArrayList<>();
        coords.add(new Coordonnees(abscisse - 1, ordonnee));     // gauche
        coords.add(new Coordonnees(abscisse, ordonnee));         // pivot
        coords.add(new Coordonnees(abscisse + 1, ordonnee));     // droite
        coords.add(new Coordonnees(abscisse + 1, ordonnee + 1)); // bas à droite
        return coords;
    }
}