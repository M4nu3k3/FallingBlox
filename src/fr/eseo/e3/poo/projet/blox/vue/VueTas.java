package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.Tas;

import java.awt.Color;
import java.awt.Graphics2D;

public class VueTas {

    public static final double MULTIPLIER_NUANCE = 0.3;

    private final Puits puits;
    private int taille;

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

    public void afficher(Graphics2D g2D) {
        Tas tas = puits.getTas();
        if (tas == null) return;

        for (Element e : tas.getElements()) {
            dessinerElement(g2D, e);
        }
    }

    private void dessinerElement(Graphics2D g2D, Element e) {
        int x = e.getCoordonnees().getAbscisse() * taille;
        int y = e.getCoordonnees().getOrdonnee() * taille;

        Color nuance = nuance(e.getCouleur().getCouleurPourAffichage());
        g2D.setColor(nuance);
        g2D.fill3DRect(x, y, taille, taille, true);

        g2D.setColor(Color.DARK_GRAY);
        g2D.drawRect(x, y, taille, taille);
    }

    public static Color nuance(Color couleur) {
        int r = couleur.getRed();
        int g = couleur.getGreen();
        int b = couleur.getBlue();

        r = (int)(r * (1 - MULTIPLIER_NUANCE));
        g = (int)(g * (1 - MULTIPLIER_NUANCE));
        b = (int)(b * (1 - MULTIPLIER_NUANCE));

        return new Color(r, g, b);
    }
}