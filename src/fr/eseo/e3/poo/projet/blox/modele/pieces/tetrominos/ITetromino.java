package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;

import java.util.ArrayList;
import java.util.List;

public class ITetromino extends Tetromino {

    public ITetromino(Couleur couleur) {
        super(new Coordonnees(4, 1), couleur); // pivot à (4,1)
    }


    public ITetromino(Coordonnees coordonnees, Couleur couleur) {
        super(coordonnees, couleur);
    }

    @Override
    protected List<Coordonnees> getFormeRelative(int abscisse, int ordonnee) {
        List<Coordonnees> coords = new ArrayList<>();
        coords.add(new Coordonnees(abscisse, ordonnee - 1)); // bloc au-dessus du pivot
        coords.add(new Coordonnees(abscisse, ordonnee));     // pivot (index 1)
        coords.add(new Coordonnees(abscisse, ordonnee + 1)); // bloc en dessous
        coords.add(new Coordonnees(abscisse, ordonnee + 2)); // bloc encore en dessous
        return coords;
    }

}