package fr.eseo.e3.poo.projet.blox.modele.pieces;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Puits;

import java.util.List;

/**
 * Interface définissant le comportement commun des pièces du jeu Blox.
 */
public interface Piece {

    /**
     * Retourne la liste des éléments qui composent la pièce.
     */
    List<Element> getElements();

    /**
     * Retourne la couleur associée à la pièce.
     */
    Couleur getCouleur();

    /**
     * Définit la position du pivot de la pièce.
     *
     * @param abscisse position en x
     * @param ordonnee position en y
     */
    void setPosition(int abscisse, int ordonnee);

    /**
     * Déplace la pièce d’un certain vecteur (dx, dy).
     *
     * @throws BloxException si le déplacement est invalide
     */
    void deplacerDe(int dx, int dy) throws BloxException;

    /**
     * Fait tourner la pièce dans le sens spécifié.
     *
     * @param sensHoraire true pour horaire, false pour antihoraire
     * @throws BloxException si la rotation est invalide
     */
    void tourner(boolean sensHoraire) throws BloxException;

    /**
     * Associe un puits à la pièce.
     */
    void setPuits(Puits puits);

    /**
     * Retourne le puits auquel la pièce est attachée.
     */
    Puits getPuits();
}