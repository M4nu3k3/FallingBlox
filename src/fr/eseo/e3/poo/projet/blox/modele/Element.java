package fr.eseo.e3.poo.projet.blox.modele;

import java.util.Objects;

/**
 * Représente un élément de pièce, défini par sa position (coordonnées)
 * et sa couleur.
 */
public class Element {

    private Coordonnees coordonnees;
    private Couleur couleur;

    /**
     * Constructeur principal avec coordonnées et couleur.
     */
    public Element(Coordonnees coordonnees, Couleur couleur) {
        this.coordonnees = coordonnees;
        this.couleur = couleur;
    }

    /**
     * Constructeur avec coordonnées, couleur par défaut.
     */
    public Element(Coordonnees coordonnees) {
        this(coordonnees, Couleur.values()[0]); // ROUGE
    }

    /**
     * Constructeur avec abscisse/ordonnée, couleur par défaut.
     */
    public Element(int abscisse, int ordonnee) {
        this(new Coordonnees(abscisse, ordonnee), Couleur.values()[0]);
    }

    /**
     * Constructeur avec abscisse/ordonnée et couleur.
     */
    public Element(int abscisse, int ordonnee, Couleur couleur) {
        this(new Coordonnees(abscisse, ordonnee), couleur);
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCoordonnees(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    /**
     * Déplace l’élément selon un vecteur (deltaX, deltaY).
     */
    public void deplacerDe(int deltaX, int deltaY) {
        this.coordonnees = new Coordonnees(
                coordonnees.getAbscisse() + deltaX,
                coordonnees.getOrdonnee() + deltaY
        );
    }

    @Override
    public String toString() {
        return "[" + couleur + " : " + coordonnees + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Element)) return false;
        Element other = (Element) obj;
        return Objects.equals(coordonnees, other.coordonnees)
                && couleur == other.couleur;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordonnees, couleur);
    }
}