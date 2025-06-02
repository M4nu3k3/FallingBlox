package fr.eseo.e3.poo.projet.blox.vue;

import fr.eseo.e3.poo.projet.blox.controleur.Gravite;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PanneauInformation extends JPanel implements PropertyChangeListener {

    public static final int TAILLE_PAR_DEFAUT = 240;

    private VuePiece vuePiece;
    private Puits puits;
    private Gravite gravite;
    private VuePuits vuePuits;
    private final JButton boutonRejouer;
    private final JButton boutonPause;
    private boolean enPause = false;

    public PanneauInformation(Puits puits) {
        this.puits = puits;
        this.puits.addPropertyChangeListener(this);
        this.setPreferredSize(new Dimension(TAILLE_PAR_DEFAUT, 480));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel labelTitre = new JLabel("Prochaine pièce :");
        labelTitre.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(labelTitre);

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
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(boutonPause);

        boutonRejouer = new JButton("\u21BB");
        boutonRejouer.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(boutonRejouer);

        this.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel scoreLabel = new JLabel("Score : 0");
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setName("scoreLabel");
        this.add(scoreLabel);

        JLabel bestScoreLabel = new JLabel("Meilleur : 0");
        bestScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bestScoreLabel.setName("bestScoreLabel");
        this.add(bestScoreLabel);

        this.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel commandes = new JLabel("\nCommandes :");
        commandes.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(commandes);

        JTextArea instructions = new JTextArea("Clavier :\n←/→ : déplacer\n↑ ou ↓ : rotation\n Shift : descente\n\nSouris :\nClic gauche : rotation\nMolette : chute\nDéplacement : translation");
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
        this.vuePiece = null;
        repaint();
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
            Piece nouvellePiece = (Piece) evt.getNewValue();
            if (nouvellePiece != null) {
                this.vuePiece = new VuePiece(nouvellePiece, TAILLE_PAR_DEFAUT / 4);
            } else {
                this.vuePiece = null;
            }
            repaint();
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (vuePiece != null) {
            Graphics2D g2D = (Graphics2D) g.create();
            g2D.translate(30, 30);
            vuePiece.afficherPiece(g2D);
            g2D.dispose();
        }
    }
}