# FallingBlox – Jeu Tetris en Java

## Objectifs

L’objectif principal était de développer un jeu Tetris fonctionnel avec une séparation claire des responsabilités selon le modèle MVC (Modèle-Vue-Contrôleur). Le projet devait intégrer :
- une gestion des pièces (tétrominos), incluant déplacements, rotations, et collisions ;
- une interface graphique dynamique réalisée avec Swing ;
- une interaction utilisateur via clavier et souris, comprenant déplacements latéraux, rotations dans les deux sens, chute rapide, et contrôle par timer (gravité) ;
- un système d’affichage des informations utiles telles que la prochaine pièce, le score courant et le meilleur score ;
- un mécanisme de pause et reprise du jeu, avec boutons dédiés.

## Fonctionnement

Le joueur contrôle des pièces qui tombent dans un puits de dimensions standards (10 colonnes par 20 lignes). Chaque pièce peut être déplacée horizontalement soit par la souris soit par le clavier, tournée dans les deux sens, et descendue rapidement grâce à la touche Shift ou la molette de la souris. La pièce se verrouille automatiquement dès qu’elle atteint le fond ou entre en collision avec d’autres éléments déjà figés (le tas).

Un panneau latéral affiche la pièce suivante, le score et fournit des indications sur les commandes. Le jeu peut être mis en pause et repris à volonté via un bouton. Le système de gravité est géré par un timer qui déclenche la descente automatique des pièces.

## Réalisation technique

Le projet a été conçu selon l’architecture MVC :
- **Modèle** : classes `Piece`, `Tetromino`, `Puits`, `Tas`;
- **Vue** : classes `VuePiece`, `VuePuits`, `VueTas`, `PanneauInformation`;
- **Contrôleurs** : classes `Clavier`, `PieceDeplacement`, `PieceRotation`, `Gravite`.

Un système d’écouteurs basé sur `PropertyChangeListener`.

Des tests unitaires JUnit ou tests d'applications

## Extensions ajoutées

- Contrôle clavier
- Système de pause/reprise complet
- Gestion du score et du meilleur score
- Fin de partie et rejouer
- Suppression d'une ligne remplie