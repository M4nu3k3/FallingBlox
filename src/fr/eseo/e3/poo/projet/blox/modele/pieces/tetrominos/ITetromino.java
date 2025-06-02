package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;

import java.util.ArrayList;
import java.util.List;

/**
 * Représentation de la pièce ITetromino
 */
public class ITetromino extends Tetromino {

    /**
     * Constructeur par défaut : pivot positionné à (4, 1)
     */
    public ITetromino(Couleur couleur) {
        super(new Coordonnees(4, 1), couleur);
    }

    /**
     * Constructeur avec coordonnées de pivot personnalisées
     */
    public ITetromino(Coordonnees coordonnees, Couleur couleur) {
        super(coordonnees, couleur);
    }

    /**
     * Détermine les coordonnées relatives des blocs de la pièce I, centrée autour du pivot fourni
     */
    @Override
    protected List<Coordonnees> getFormeRelative(int abscisse, int ordonnee) {
        List<Coordonnees> coords = new ArrayList<>();
        coords.add(new Coordonnees(abscisse, ordonnee - 1)); // au-dessus du pivot
        coords.add(new Coordonnees(abscisse, ordonnee));     // pivot (index 1)
        coords.add(new Coordonnees(abscisse, ordonnee + 1)); // en dessous
        coords.add(new Coordonnees(abscisse, ordonnee + 2)); // encore en dessous
        return coords;
    }
}