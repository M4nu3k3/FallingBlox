package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.Tas;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Représente graphiquement le tas d’éléments figés dans le puits.
 */
public class VueTas {

    public static final double MULTIPLIER_NUANCE = 0.3;

    private final Puits puits;
    private int taille;

    /**
     * Construit une vue du tas pour un puits donné avec une taille de case.
     */
    public VueTas(Puits puits, int taille) {
        this.puits = puits;
        this.taille = taille;
    }

    public Puits getVuePuits() {
        return puits;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    /**
     * Affiche tous les éléments du tas.
     */
    public void afficher(Graphics2D g2D) {
        Tas tas = puits.getTas();
        if (tas == null) return;

        for (Element e : tas.getElements()) {
            dessinerElement(g2D, e);
        }
    }

    /**
     * Dessine un élément du tas avec une couleur assombrie.
     */
    private void dessinerElement(Graphics2D g2D, Element e) {
        int x = e.getCoordonnees().getAbscisse() * taille;
        int y = e.getCoordonnees().getOrdonnee() * taille;

        Color nuance = nuance(e.getCouleur().getCouleurPourAffichage());
        g2D.setColor(nuance);
        g2D.fill3DRect(x, y, taille, taille, true);

        g2D.setColor(Color.DARK_GRAY);
        g2D.drawRect(x, y, taille, taille);
    }

    /**
     * Applique une nuance plus sombre à une couleur (effet d’ombrage).
     */
    public static Color nuance(Color couleur) {
        int r = (int) (couleur.getRed() * (1 - MULTIPLIER_NUANCE));
        int g = (int) (couleur.getGreen() * (1 - MULTIPLIER_NUANCE));
        int b = (int) (couleur.getBlue() * (1 - MULTIPLIER_NUANCE));
        return new Color(r, g, b);
    }
}