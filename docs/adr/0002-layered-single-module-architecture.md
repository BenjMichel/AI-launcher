# 0002 — Architecture en couches, module Gradle unique

## Contexte

Le projet sert aussi de vitrine de compétences en architecture Android. Deux options ont été considérées : un multi-module Gradle (`:app`, `:core-domain`, `:core-data`, `:feature-home`…) ou une séparation logique par package dans un module unique.

## Décision

Un seul module `:app`, séparé en packages `domain` / `data` / `di` / `presentation` / `ui`, avec dépendance à sens unique (`presentation → domain ← data`) et inversion de dépendance : les interfaces vivent dans `domain`, leurs implémentations Android dans `data`.

## Conséquences

- La même discipline architecturale (domain pur, testable en JVM sans Android/Robolectric ; data isolée derrière des interfaces ; DI via Hilt) est démontrée sans le coût de build et le boilerplate d'un multi-module, disproportionné pour une app de cette taille (horloge + 5 favoris + recherche).
- Le découpage en packages est délibérément aligné sur les futures frontières de modules (`domain`, `data`, `feature-home`…) : si le projet grossit significativement (météo, agenda, plusieurs écrans), l'extraction en modules Gradle réels sera mécanique plutôt qu'un refactor de fond.
- Compromis assumé : `domain/repository/AppIconProvider` retourne un `androidx.compose.ui.graphics.ImageBitmap`, un type Compose UI plutôt qu'un type 100% agnostique de la plateforme. Une architecture "pure" isolerait aussi ce type hors du domain ; ça a été jugé disproportionné pour éviter une couche de mapping supplémentaire sur un simple bitmap d'icône.
