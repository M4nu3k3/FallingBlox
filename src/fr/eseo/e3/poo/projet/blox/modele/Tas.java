package fr.eseo.e3.poo.projet.blox.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

/**
 * Représente le tas d'éléments figés en bas du puits.
 * Sert à détecter les collisions, stocker les pièces posées,
 * et gérer la suppression des lignes complètes.
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

        List<Coordonnees> positions = new ArrayList<>();
        for (int y = puits.getHauteur() - nbLignes; y < puits.getHauteur(); y++) {
            for (int x = 0; x < puits.getLargeur(); x++) {
                positions.add(new Coordonnees(x, y));
            }
        }

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

    /* === Gestion des lignes complètes === */

    /**
     * Détecte et supprime toutes les lignes complètes dans le tas.
     * Après suppression, les éléments au-dessus descendent d'une case.
     * @return Le nombre de lignes supprimées.
     */
    public int detecterEtSupprimerLignesComplete() {
        int lignesSupprimees = 0;

        // On parcourt de bas en haut
        for (int y = puits.getHauteur() - 1; y >= 0; y--) {
            if (estLigneComplete(y)) {
                supprimerLigne(y);
                lignesSupprimees++;
                y++; // Revenir à la même ligne pour revérifier après descente
            }
        }

        return lignesSupprimees;
    }

    /**
     * Vérifie si une ligne est complète (toutes les colonnes occupées).
     */
    private boolean estLigneComplete(int y) {
        for (int x = 0; x < puits.getLargeur(); x++) {
            if (!contientCoordonnees(x, y)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Supprime tous les éléments sur la ligne donnée
     * et fait descendre d'une case les éléments au-dessus.
     */
    private void supprimerLigne(int y) {
        // Suppression des éléments de la ligne y
        elements.removeIf(e -> e.getCoordonnees().getOrdonnee() == y);

        // Descente d’une case des éléments au-dessus
        for (Element e : elements) {
            Coordonnees c = e.getCoordonnees();
            if (c.getOrdonnee() < y) {
                e.setCoordonnees(new Coordonnees(c.getAbscisse(), c.getOrdonnee() + 1));
            }
        }
    }
}