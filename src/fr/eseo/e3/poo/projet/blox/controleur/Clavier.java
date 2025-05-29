//package fr.eseo.e3.poo.projet.blox.controleur;
//
//import fr.eseo.e3.poo.projet.blox.modele.Puits;
//import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;
//import fr.eseo.e3.poo.projet.blox.vue.VuePuits;
//
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//
//public class Clavier extends KeyAdapter {
//
//    private final Puits puits;
//    private final VuePuits vue;
//
//    public Clavier(Puits puits, VuePuits vue) {
//        this.puits = puits;
//        this.vue = vue;
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//        Piece piece = puits.getPieceActuelle();
//        if (piece == null) return;
//
//        switch (e.getKeyCode()) {
//            case KeyEvent.VK_LEFT:
//                piece.deplacerDe(-1, 0);
//                break;
//            case KeyEvent.VK_RIGHT:
//                piece.deplacerDe(1, 0);
//                break;
//            case KeyEvent.VK_DOWN:
//                piece.deplacerDe(0, 1);
//                break;
//            case KeyEvent.VK_UP:
//                piece.tourner();
//                break;
//        }
//
//        vue.repaint();
//    }
//}