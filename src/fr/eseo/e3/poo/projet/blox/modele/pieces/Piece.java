package fr.eseo.e3.poo.projet.blox.modele.pieces;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Puits;

import java.util.List;

/**
 * Interface qui regroupe tout ce qu’une pièce doit savoir faire dans le jeu :
 * se déplacer, tourner, changer de position, avoir une couleur, et être liée à un puits.
 * Toutes les pièces (comme les tetrominos) vont devoir suivre ce modèle.
 */
public interface Piece {

    List<Element> getElements();

    Couleur getCouleur();

    void setPosition(int abscisse, int ordonnee);

    void deplacerDe(int dx, int dy) throws BloxException;

    void tourner(boolean sensHoraire) throws BloxException;

    void setPuits(Puits puits);

    Puits getPuits();
}