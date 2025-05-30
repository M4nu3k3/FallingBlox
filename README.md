# FallingBlox – Jeu Tetris en Java

Projet en cours réalisé dans le cadre de ma formation à l’ESEO.  
FallingBlox est une implémentation du jeu Tetris en Java avec une architecture propre, basée sur le modèle MVC. L’objectif est de construire une version stable, interactive et évolutive du jeu, tout en respectant les contraintes fonctionnelles d’un Tetris classique.

## Objectif

Concevoir un jeu complet en Java avec une séparation claire entre le modèle, la vue et le contrôleur.  
Travailler sur l’affichage graphique, les interactions utilisateur (clavier et souris), la gestion des pièces et des collisions, tout en structurant le projet de manière modulaire et maintenable.

## Fonctionnement

Le joueur fait tomber des pièces Tetris dans un puits. Chaque pièce peut être déplacée latéralement, tournée, et descend automatiquement. Le but est de former des lignes complètes pour les faire disparaître. Le contrôle se fait à la souris et au clavier, avec notamment :
- rotation horaire/antihoraire,
- déplacement latéral en fonction de la position du curseur,
- verrouillage automatique d’une pièce une fois posée.

## Réalisation

Le projet utilise :
- Java (sans librairies externes),
- une architecture MVC avec écouteurs/observateurs,
- un affichage en Swing (vue personnalisée),
- un système de `Piece`, `Puits`, `Tas`, `VuePiece`, `VuePuits`, `Controleurs`, etc.

Des tests JUnit accompagnent les classes du modèle pour vérifier les comportements (rotations, collisions, placements valides…).

Le projet continue d’évoluer au fil de l’implémentation de nouvelles fonctionnalités : gestion du score, niveau de difficulté, affichage de la prochaine pièce, animations, etc.

## Ce que ça m’a appris

Ce projet m’a permis de mieux maîtriser :
- la gestion d’un projet Java modulaire,
- la logique de jeu et les contraintes d’un moteur Tetris,
- l’interaction entre les événements utilisateur et l’affichage graphique,
- les bonnes pratiques de structuration logicielle (MVC, encapsulation, responsabilité des classes).
