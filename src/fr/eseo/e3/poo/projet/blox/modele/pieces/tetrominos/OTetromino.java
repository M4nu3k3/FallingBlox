package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Tetromino;

import java.util.ArrayList;
import java.util.List;

/**
 * Représentation de la pièce O (carré 2x2), sans rotation.
 */
public class OTetromino extends Tetromino {

    /**
     * Constructeur par défaut avec pivot à (4, 0) et couleur rouge.
     */
    public OTetromino() {
        super(new Coordonnees(4, 0), Couleur.ROUGE);
    }

    /**
     * Constructeur avec couleur personnalisée (pivot à (4, 0)).
     */
    public OTetromino(Couleur couleur) {
        super(new Coordonnees(4, 0), couleur);
    }

    /**
     * Constructeur avec pivot et couleur personnalisés.
     */
    public OTetromino(Coordonnees coordonnees, Couleur couleur) {
        super(coordonnees, couleur);
    }

    /**
     * Retourne les coordonnées relatives des 4 blocs de la pièce O.
     * Cette pièce ne tourne pas, car sa forme est symétrique.
     */
    @Override
    protected List<Coordonnees> getFormeRelative(int abscisse, int ordonnee) {
        List<Coordonnees> coords = new ArrayList<>();
        coords.add(new Coordonnees(abscisse, ordonnee));
        coords.add(new Coordonnees(abscisse + 1, ordonnee));
        coords.add(new Coordonnees(abscisse, ordonnee + 1));
        coords.add(new Coordonnees(abscisse + 1, ordonnee + 1));
        return coords;
    }

    /**
     * Surcharge vide : la pièce O ne tourne jamais.
     */
    @Override
    public void tourner(boolean sensHoraire) {
        // Pas de rotation pour la pièce O
    }

    /**
     * Affichage textuel de la pièce (liste des éléments).
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("OTetromino:");
        for (var e : getElements()) {
            sb.append("\n\t").append(e.toString());
        }
        return sb.toString();
    }
}