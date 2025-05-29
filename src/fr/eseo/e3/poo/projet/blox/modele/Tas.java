package fr.eseo.e3.poo.projet.blox.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

/**
 * Représente le tas d'éléments figés en bas du puits.
 * Sert à détecter les collisions et à stocker les pièces posées.
 */
public class Tas {

    private final List<Element> elements;
    private final Puits puits;

    /* === Constructeurs === */

    public Tas() {
        this.puits = null;
        this.elements = new ArrayList<>();
    }

    public Tas(Puits puits) {
        this.puits = puits;
        this.elements = new ArrayList<>();
    }

    public Tas(Puits puits, int nbElements) {
        this(puits, nbElements, (nbElements / puits.getLargeur()) + 1);
    }

    public Tas(Puits puits, int nbElements, int nbLignes) {
        this(puits, nbElements, nbLignes, new Random());
    }

    public Tas(Puits puits, int nbElements, int nbLignes, Random rand) {
        this.puits = puits;
        this.elements = new ArrayList<>();
        construireTas(nbElements, nbLignes, rand);
    }

    /* === Accesseurs === */

    public Puits getPuits() {
        return puits;
    }

    public List<Element> getElements() {
        return elements;
    }

    /* === Construction du tas aléatoire === */

    public void construireTas(int nbElements, int nbLignes, Random rand) {
        if (puits == null) {
            throw new IllegalStateException("Impossible de construire un tas sans puits.");
        }

        if (nbLignes > puits.getHauteur()) {
            throw new IllegalArgumentException("Le nombre de lignes dépasse la hauteur du puits.");
        }

        if (nbElements > puits.getLargeur() * nbLignes) {
            throw new IllegalArgumentException("Trop d'éléments pour l'espace disponible.");
        }

        // Génère toutes les positions possibles dans la zone définie
        List<Coordonnees> positions = new ArrayList<>();
        for (int y = puits.getHauteur() - nbLignes; y < puits.getHauteur(); y++) {
            for (int x = 0; x < puits.getLargeur(); x++) {
                positions.add(new Coordonnees(x, y));
            }
        }

        // Ajoute aléatoirement les éléments dans le tas
        for (int i = 0; i < nbElements; i++) {
            int index = rand.nextInt(positions.size());
            Coordonnees coord = positions.remove(index);
            Couleur couleur = Couleur.values()[rand.nextInt(Couleur.values().length)];
            elements.add(new Element(coord, couleur));
        }
    }

    /* === Recherches et collisions === */

    public boolean contientCoordonnees(int x, int y) {
        for (Element e : elements) {
            Coordonnees c = e.getCoordonnees();
            if (c.getAbscisse() == x && c.getOrdonnee() == y) {
                return true;
            }
        }
        return false;
    }

    public boolean contient(Piece piece) {
        for (Element e : piece.getElements()) {
            if (!elements.contains(e)) {
                return false;
            }
        }
        return true;
    }

    /* === Ajouts === */

    public void ajouterPiece(Piece piece) {
        elements.addAll(piece.getElements());
    }

    public void ajouterElements(Piece piece) {
        for (Element e : piece.getElements()) {
            elements.add(new Element(e.getCoordonnees(), e.getCouleur()));
        }
    }
}