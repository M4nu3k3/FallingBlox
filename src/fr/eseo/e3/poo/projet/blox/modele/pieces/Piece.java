package fr.eseo.e3.poo.projet.blox.modele.pieces;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Puits;

import java.util.List;

public interface Piece {
    List<Element> getElements();
    Couleur getCouleur();
    void setPosition(int abscisse, int ordonnee);
    void deplacerDe(int dx, int dy) throws BloxException;
    void tourner(boolean sensHoraire) throws BloxException;
    void setPuits(Puits puits);
    Puits getPuits();
}