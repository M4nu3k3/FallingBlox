package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;

import java.util.ArrayList;
import java.util.List;

public class OTetromino extends Tetromino {

    public OTetromino(Couleur couleur) {
        super(new Coordonnees(4, 0), couleur);
    }

    public OTetromino(Coordonnees coordonnees, Couleur couleur) {
        super(coordonnees, couleur);
    }

    public OTetromino() {
        super(new Coordonnees(4, 0), Couleur.ROUGE);
    }

    @Override
    protected List<Coordonnees> getFormeRelative(int abscisse, int ordonnee) {
        List<Coordonnees> coords = new ArrayList<>();
        coords.add(new Coordonnees(abscisse, ordonnee));
        coords.add(new Coordonnees(abscisse + 1, ordonnee));
        coords.add(new Coordonnees(abscisse, ordonnee + 1));
        coords.add(new Coordonnees(abscisse + 1, ordonnee + 1));
        return coords;
    }

    @Override
    public void tourner(boolean sensHoraire) {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("OTetromino:");
        for (var e : getElements()) {
            sb.append("\n\t").append(e.toString());
        }
        return sb.toString();
    }
}
