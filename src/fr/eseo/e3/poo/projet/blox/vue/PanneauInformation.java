package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.controleur.Gravite;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class PanneauInformation extends JPanel implements PropertyChangeListener {

    public static final int TAILLE_PAR_DEFAUT = 240;
    private static final int TAILLE_CASE_SUIVANTE = 16;

    private Puits puits;
    private Gravite gravite;
    private VuePuits vuePuits;
    private final JButton boutonRejouer;
    private final JButton boutonPause;
    private final JPanel panneauPieceSuivante;
    private boolean enPause = false;

    private List<Element> elements;
    private int tailleCase;

    public PanneauInformation(Puits puits) {
        this.puits = puits;
        this.puits.addPropertyChangeListener(this);
        this.setPreferredSize(new Dimension(TAILLE_PAR_DEFAUT, 500));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel labelTitre = new JLabel("Prochaine pièce :");
        labelTitre.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createVerticalStrut(10));
        this.add(labelTitre);

        panneauPieceSuivante = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (elements != null) {
                    Graphics2D g2D = (Graphics2D) g.create();
                    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
                    int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
                    for (Element e : elements) {
                        int x = e.getCoordonnees().getAbscisse();
                        int y = e.getCoordonnees().getOrdonnee();
                        if (x < minX) minX = x;
                        if (x > maxX) maxX = x;
                        if (y < minY) minY = y;
                        if (y > maxY) maxY = y;
                    }

                    int largeur = (maxX - minX + 1) * tailleCase;
                    int hauteur = (maxY - minY + 1) * tailleCase;
                    int offsetX = (getWidth() - largeur) / 2 - minX * tailleCase;
                    int offsetY = (getHeight() - hauteur) / 2 - minY * tailleCase;

                    g2D.translate(offsetX, offsetY);

                    for (int i = 0; i < elements.size(); i++) {
                        Element e = elements.get(i);
                        int x = e.getCoordonnees().getAbscisse() * tailleCase;
                        int y = e.getCoordonnees().getOrdonnee() * tailleCase;

                        Couleur couleur = e.getCouleur();
                        Color color = couleur.getCouleurPourAffichage();
                        Color teinte = i == 1 ? VuePiece.teinte(color) : color;

                        g2D.setColor(teinte);
                        g2D.fill3DRect(x, y, tailleCase, tailleCase, true);
                        g2D.setColor(Color.DARK_GRAY);
                        g2D.drawRect(x, y, tailleCase, tailleCase);

                        if (i == 1) {
                            int r = tailleCase / 2;
                            int m = (tailleCase - r) / 2;
                            g2D.setColor(new Color(255, 255, 255, 128));
                            g2D.fillOval(x + m, y + m, r, r);
                            g2D.setColor(Color.GRAY);
                            g2D.setStroke(new BasicStroke(2));
                            g2D.drawOval(x + m, y + m, r, r);
                            g2D.setStroke(new BasicStroke(1));
                        }
                    }

                    g2D.dispose();
                }
            }
        };
        panneauPieceSuivante.setPreferredSize(new Dimension(100, 100));
        panneauPieceSuivante.setMaximumSize(new Dimension(120, 120));
        panneauPieceSuivante.setBackground(Color.WHITE);
        panneauPieceSuivante.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(panneauPieceSuivante);

        // Laisser de l'espace après l'affichage de la pièce
        this.add(Box.createVerticalStrut(15));

        boutonPause = new JButton("\u23F8");
        boutonPause.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gravite != null) {
                    if (enPause) {
                        gravite.start();
                        vuePuits.reconnectListeners();
                        boutonPause.setText("\u23F8");
                    } else {
                        gravite.stop();
                        vuePuits.deconnectListeners();
                        boutonPause.setText("\u25B6");
                    }
                    enPause = !enPause;
                }
            }
        });
        this.add(boutonPause);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        boutonRejouer = new JButton("\u21BB");
        boutonRejouer.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(boutonRejouer);
        this.add(Box.createVerticalStrut(20));

        JLabel scoreLabel = new JLabel("Score : 0");
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setName("scoreLabel");
        this.add(scoreLabel);

        JLabel bestScoreLabel = new JLabel("Meilleur : 0");
        bestScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bestScoreLabel.setName("bestScoreLabel");
        this.add(bestScoreLabel);

        this.add(Box.createVerticalStrut(20));

        JLabel commandes = new JLabel("Commandes :");
        commandes.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(commandes);

        JTextArea instructions = new JTextArea("Clavier :\n←/→ : déplacer\n↑ ou ↓ : rotation\nShift : descente\n\nSouris :\nClic gauche : rotation\nMolette : chute\nDéplacement : translation");
        instructions.setEditable(false);
        instructions.setFont(new Font("Monospaced", Font.PLAIN, 11));
        instructions.setBackground(getBackground());
        this.add(instructions);
    }

    public void setPuits(Puits puits) {
        if (this.puits != null) {
            this.puits.removePropertyChangeListener(this);
        }
        this.puits = puits;
        this.puits.addPropertyChangeListener(this);
        this.elements = null;
        repaint();
        panneauPieceSuivante.repaint();
    }

    public void setGravite(Gravite gravite) {
        this.gravite = gravite;
    }

    public void setVuePuits(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
    }

    public JButton getBoutonRejouer() {
        return boutonRejouer;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Puits.MODIFICATION_PIECE_SUIVANTE.equals(evt.getPropertyName())) {
            Piece piece = (Piece) evt.getNewValue();
            if (piece != null) {
                this.elements = piece.getElements();
                this.tailleCase = TAILLE_CASE_SUIVANTE;
            } else {
                this.elements = null;
            }
            panneauPieceSuivante.repaint();
        } else if ("score".equals(evt.getPropertyName())) {
            for (Component c : this.getComponents()) {
                if (c instanceof JLabel && "scoreLabel".equals(c.getName())) {
                    ((JLabel) c).setText("Score : " + evt.getNewValue());
                }
                if (c instanceof JLabel && "bestScoreLabel".equals(c.getName())) {
                    ((JLabel) c).setText("Meilleur : " + puits.getMeilleurScore());
                }
            }
        }
    }
}