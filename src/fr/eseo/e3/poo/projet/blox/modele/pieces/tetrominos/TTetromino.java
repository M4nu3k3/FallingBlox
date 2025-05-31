package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;

import java.util.ArrayList;
import java.util.List;

public class TTetromino extends Tetromino {

    public TTetromino(Couleur couleur) {
        super(new Coordonnees(4, 1), couleur);  // pivot au centre
    }

    @Override
    protected List<Coordonnees> getFormeRelative(int abscisse, int ordonnee) {
        List<Coordonnees> coords = new ArrayList<>();
        // Forme T :
        coords.add(new Coordonnees(abscisse - 1, ordonnee));   // gauche
        coords.add(new Coordonnees(abscisse, ordonnee));       // pivot (index 1)
        coords.add(new Coordonnees(abscisse + 1, ordonnee));   // droite
        coords.add(new Coordonnees(abscisse, ordonnee + 1));   // bas
        return coords;
    }
}