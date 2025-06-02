# FallingBlox – Jeu Tetris en Java

## Objectifs

L’objectif principal était de développer un jeu Tetris fonctionnel avec une séparation claire des responsabilités selon le modèle MVC (Modèle-Vue-Contrôleur). Le projet devait intégrer :
- une gestion précise des pièces (tétrominos), incluant déplacements, rotations, et collisions ;
- une interface graphique dynamique réalisée avec Swing ;
- une interaction utilisateur via clavier et souris, comprenant déplacements latéraux, rotations dans les deux sens, chute rapide, et contrôle par timer (gravité) ;
- un système d’affichage des informations utiles telles que la prochaine pièce, le score courant et le meilleur score ;
- un mécanisme de pause et reprise du jeu, avec boutons dédiés.

## Fonctionnement

Le joueur contrôle des pièces qui tombent dans un puits de dimensions standards (10 colonnes par 20 lignes). Chaque pièce peut être déplacée horizontalement soit par la souris soit par le clavier, tournée dans les deux sens, et descendue rapidement grâce à la touche Shift ou la molette de la souris. La pièce se verrouille automatiquement dès qu’elle atteint le fond ou entre en collision avec d’autres éléments déjà figés (le tas).

Un panneau latéral affiche la pièce suivante, le score et fournit des indications sur les commandes. Le jeu peut être mis en pause et repris à volonté via un bouton. Le système de gravité est géré par un timer qui déclenche la descente automatique des pièces.

## Réalisation technique

Le projet a été conçu selon l’architecture MVC :
- **Modèle** : classes `Piece`, `Tetromino`, `Puits`, `Tas`, qui gèrent la logique métier, les règles du jeu, et la gestion des collisions ;
- **Vue** : classes `VuePiece`, `VuePuits`, `VueTas`, `PanneauInformation`, responsables de l’affichage graphique, du rendu des pièces et des éléments du jeu, ainsi que de l’interface utilisateur ;
- **Contrôleurs** : classes `Clavier`, `PieceDeplacement`, `PieceRotation`, `Gravite`, qui assurent la gestion des interactions utilisateurs (clavier, souris) et la gestion temporelle (gravité).

Un système d’écouteurs basé sur `PropertyChangeListener` permet une communication efficace et découplée entre modèle et vue, garantissant la mise à jour automatique de l’affichage en réponse aux changements du modèle.

Des tests unitaires JUnit ont été développés pour valider les fonctionnalités essentielles telles que les rotations, les déplacements et la détection des collisions.


## Extensions ajoutées

En plus des fonctionnalités de base d’un Tetris classique, j’ai intégré plusieurs extensions visant à améliorer le gameplay et l’ergonomie :

- **Rotation bidirectionnelle** : la pièce peut être tournée dans les deux sens (horaire et antihoraire), avec gestion des collisions et retour en arrière si la rotation est impossible.
- **Contrôle fin à la souris** : déplacement latéral par déplacement du curseur avec limitation à un déplacement d’une colonne par mouvement, ainsi que descente rapide via la molette de la souris.
- **Système de pause/reprise complet** : grâce à un bouton dédié, avec suspension de la gravité et désactivation des contrôles pendant la pause.
- **Affichage graphique amélioré** : mise en évidence du pivot de la pièce par un indicateur graphique, nuances de couleurs pour les éléments figés (tas), et panneau d’information latéral complet (pièce suivante, score, commandes).
- **Gestion du score et du meilleur score** : mise à jour dynamique avec notification graphique.
- **Fin de partie et rejouer** : possibilité de recommencer une partie proprement via un bouton de « rejouer » avec réinitialisation complète de l’état du jeu.
- **Suppression d'une ligne remplie**
- 
## Bilan personnel

Ce projet m’a permis d’approfondir mes connaissances en programmation orientée objet et en conception logicielle modulaire. J’ai renforcé ma maîtrise de Java Swing pour le développement d’interfaces graphiques réactives, ainsi que la gestion avancée des événements utilisateurs. La mise en place d’un système MVC complet m’a sensibilisé aux bonnes pratiques de structuration d’application.

La gestion fine des règles du jeu Tetris, notamment les rotations complexes, la détection de collisions et la synchronisation entre modèle et vues, a constitué un challenge intéressant. Ce travail m’a également apporté une expérience précieuse en tests unitaires et en développement itératif.

Enfin, la conception d’une interface utilisateur ergonomique, incluant les commandes via clavier et souris ainsi que les contrôles de pause/reprise, m’a permis de prendre en compte l’aspect expérience utilisateur dans le développement logiciel.

---

Ce projet reste évolutif et pourra être enrichi ultérieurement par l’ajout de fonctionnalités telles que des niveaux de difficulté, un système de sauvegarde, ou une interface améliorée.

