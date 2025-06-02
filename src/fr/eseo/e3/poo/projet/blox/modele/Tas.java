package fr.eseo.e3.poo.projet.blox.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

/**
 * Représente le tas de blocs posés au fond du puits, ainsi que
 * la détection/suppression des lignes complètes et les collisions hautes.
 */
public class Tas {

    private final List<Element> elements;  // Liste des éléments présents dans le tas
    private final Puits puits;             // Référence vers le puits associé

    // Constructeurs
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

    // Accesseurs
    public Puits getPuits() {
        return puits;
    }

    public List<Element> getElements() {
        return elements;
    }

    /**
     * Construit un tas initial aléatoire selon les dimensions et nombre d'éléments.
     */
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

    /**
     * Vérifie si un élément du tas est présent à la coordonnée (x, y).
     */
    public boolean contientCoordonnees(int x, int y) {
        for (Element e : elements) {
            Coordonnees c = e.getCoordonnees();
            if (c.getAbscisse() == x && c.getOrdonnee() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si tous les éléments d’une pièce sont déjà dans le tas.
     */
    public boolean contient(Piece piece) {
        for (Element e : piece.getElements()) {
            if (!elements.contains(e)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Ajoute tous les éléments d’une pièce au tas (références conservées).
     */
    public void ajouterPiece(Piece piece) {
        elements.addAll(piece.getElements());
    }

    /**
     * Ajoute une copie des éléments d'une pièce au tas, puis vérifie une collision haute.
     */
    public void ajouterElements(Piece piece) {
        for (Element e : piece.getElements()) {
            Coordonnees coord = e.getCoordonnees();
            elements.add(new Element(coord, e.getCouleur()));
        }

        if (detecterCollisionHaut(piece)) {
            if (puits != null && !puits.isPartieTerminee()) {
                puits.setPartieTerminee(true);
            }
        }
    }

    /**
     * Détecte si un des éléments est sur la ligne 0 (condition de fin).
     */
    public boolean detecterCollisionHaut(Piece piece) {
        for (Element e : piece.getElements()) {
            if (e.getCoordonnees().getOrdonnee() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Supprime toutes les lignes complètes et décale le reste vers le bas.
     *
     * @return le nombre de lignes supprimées
     */
    public int detecterEtSupprimerLignesComplete() {
        int lignesSupprimees = 0;

        for (int y = puits.getHauteur() - 1; y >= 0; y--) {
            if (estLigneComplete(y)) {
                supprimerLigne(y);
                lignesSupprimees++;
                y++; // Vérifie à nouveau cette ligne après décalage
            }
        }

        return lignesSupprimees;
    }

    /**
     * Vérifie si une ligne est complètement remplie.
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
     * Supprime tous les éléments sur la ligne donnée et décale ceux au-dessus.
     */
    private void supprimerLigne(int y) {
        elements.removeIf(e -> e.getCoordonnees().getOrdonnee() == y);

        for (Element e : elements) {
            Coordonnees c = e.getCoordonnees();
            if (c.getOrdonnee() < y) {
                e.setCoordonnees(new Coordonnees(c.getAbscisse(), c.getOrdonnee() + 1));
            }
        }
    }
}