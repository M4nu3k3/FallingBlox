package fr.eseo.e3.poo.projet.blox.modele.pieces;

import fr.eseo.e3.poo.projet.blox.modele.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant un Tétromino du jeu Blox.
 * Implémente l'interface Piece et fournit les comportements communs.
 */
public abstract class Tetromino implements Piece {

    protected final List<Element> elements = new ArrayList<>();
    protected Puits puits;

    /**
     * Constructeur : initialise la pièce à partir de coordonnées et d'une couleur.
     */
    public Tetromino(Coordonnees coordonnees, Couleur couleur) {
        setPosition(coordonnees.getAbscisse(), coordonnees.getOrdonnee(), couleur);
    }

    @Override
    public List<Element> getElements() {
        return elements;
    }

    @Override
    public Couleur getCouleur() {
        return elements.isEmpty() ? null : elements.get(0).getCouleur();
    }

    @Override
    public void setPuits(Puits puits) {
        this.puits = puits;
    }

    @Override
    public Puits getPuits() {
        return this.puits;
    }

    @Override
    public void setPosition(int abscisse, int ordonnee) {
        setPosition(abscisse, ordonnee, getCouleur());
    }

    /**
     * Réinitialise la forme de la pièce autour d’un nouveau pivot.
     */
    protected void setPosition(int abscisse, int ordonnee, Couleur couleur) {
        elements.clear();
        List<Coordonnees> coords = getFormeRelative(abscisse, ordonnee);
        for (Coordonnees c : coords) {
            elements.add(new Element(c, couleur));
        }
    }

    /**
     * Déplace la pièce selon un vecteur (dx, dy), en vérifiant les collisions.
     */
    @Override
    public void deplacerDe(int dx, int dy) throws BloxException {
        if (!((dx == -1 && dy == 0) || (dx == 1 && dy == 0) || (dx == 0 && dy == 1))) {
            throw new IllegalArgumentException();
        }

        if (puits == null) return;

        for (Element e : elements) {
            int newX = e.getCoordonnees().getAbscisse() + dx;
            int newY = e.getCoordonnees().getOrdonnee() + dy;

            if (newX < 0 || newX >= puits.getLargeur()) {
                throw new BloxException("Sortie du puits détectée", BloxException.BLOX_SORTIE_PUITS);
            }

            if (newY >= puits.getHauteur() || puits.getTas().contientCoordonnees(newX, newY)) {
                throw new BloxException("Collision détectée", BloxException.BLOX_COLLISION);
            }
        }

        for (Element e : elements) {
            e.deplacerDe(dx, dy);
        }
    }

    /**
     * Met à jour les coordonnées de chaque élément avec une nouvelle liste.
     */
    protected void miseAJourForme(List<Coordonnees> nouvellesCoordonnees) {
        for (int i = 0; i < getElements().size(); i++) {
            this.getElements().get(i).setCoordonnees(nouvellesCoordonnees.get(i));
        }
    }

    /**
     * Applique une rotation horaire ou antihoraire autour du pivot.
     * Déclenche une exception si la rotation génère une collision ou une sortie du puits.
     */
    @Override
    public void tourner(boolean sensHoraire) throws BloxException {
        if (puits == null || elements.size() != 4) return;

        Coordonnees pivot = elements.get(1).getCoordonnees(); // Pivot = 2e élément
        int px = pivot.getAbscisse();
        int py = pivot.getOrdonnee();

        List<Coordonnees> nouvellesCoordonnees = new ArrayList<>();

        for (Element e : elements) {
            Coordonnees c = e.getCoordonnees();
            int dx = c.getAbscisse() - px;
            int dy = c.getOrdonnee() - py;

            int newX, newY;
            if (sensHoraire) {
                newX = px + dy;
                newY = py - dx;
            } else {
                newX = px - dy;
                newY = py + dx;
            }

            if (newX < 0 || newX >= puits.getLargeur()) {
                throw new BloxException("Sortie du puits détectée", BloxException.BLOX_SORTIE_PUITS);
            }

            if (newY < 0 || newY >= puits.getHauteur() || puits.getTas().contientCoordonnees(newX, newY)) {
                throw new BloxException("Collision détectée", BloxException.BLOX_COLLISION);
            }

            nouvellesCoordonnees.add(new Coordonnees(newX, newY));
        }

        miseAJourForme(nouvellesCoordonnees);
    }

    /**
     * Indique si la pièce peut descendre sans collision.
     */
    public boolean peutDescendre() {
        if (puits == null) return false;

        for (Element e : elements) {
            int x = e.getCoordonnees().getAbscisse();
            int y = e.getCoordonnees().getOrdonnee() + 1;

            if (y >= puits.getHauteur() || puits.getTas().contientCoordonnees(x, y)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Méthode à implémenter par les sous-classes pour définir la forme relative.
     */
    protected abstract List<Coordonnees> getFormeRelative(int abscisse, int ordonnee);
}