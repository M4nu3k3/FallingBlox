package fr.eseo.e3.poo.projet.blox.modele;

import java.util.Objects;

/**
 * Représente une paire de coordonnées (x, y) dans le puits.
 */
public class Coordonnees {

    private int abscisse;
    private int ordonnee;

    /**
     * Crée une nouvelle instance de coordonnées.
     *
     * @param abscisse position horizontale
     * @param ordonnee position verticale
     */
    public Coordonnees(int abscisse, int ordonnee) {
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
    }

    public int getAbscisse() {
        return abscisse;
    }

    public int getOrdonnee() {
        return ordonnee;
    }

    public void setAbscisse(int abscisse) {
        this.abscisse = abscisse;
    }

    public void setOrdonnee(int ordonnee) {
        this.ordonnee = ordonnee;
    }

    @Override
    public String toString() {
        return "(" + abscisse + ", " + ordonnee + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Coordonnees)) return false;
        Coordonnees other = (Coordonnees) obj;
        return abscisse == other.abscisse && ordonnee == other.ordonnee;
    }

    @Override
    public int hashCode() {
        return Objects.hash(abscisse, ordonnee);
    }
}