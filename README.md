# Launcher

Un launcher Android minimaliste, inspiré d'[Olauncher](https://play.google.com/store/apps/details?id=app.olauncher) : l'écran d'accueil comme point de calme plutôt que comme grille d'icônes.

## Scope v1

- Horloge.
- 5 raccourcis vers des apps favorites (tap pour lancer, tap sur un slot vide ou appui long pour changer l'app assignée).
- Recherche qui filtre en temps réel la liste des apps installées, pour en lancer n'importe laquelle.

Vision plus long terme (hors scope v1, à ne pas anticiper dans le code actuel) : intégrer directement des éléments comme la météo ou l'agenda, pour que l'écran d'accueil devienne un véritable assistant plutôt qu'une simple collection d'apps. iOS est explicitement hors scope pour l'instant — Apple ne permet pas de remplacer l'écran d'accueil système sans jailbreak.

## Stack

- Kotlin + Jetpack Compose (Material 3), natif Android — pas de framework cross-platform : ce projet n'a besoin de rien qu'Android ne fournisse déjà, et parler directement aux API `HOME`/`PackageManager` évite une couche de bridge.
- Hilt pour l'injection de dépendances.
- Jetpack DataStore (Preferences) pour la persistance des favoris.
- JUnit + kotlinx-coroutines-test + Turbine pour les tests.

## Architecture

Un seul module Gradle (`:app`), organisé en 3 couches par package plutôt qu'en modules Gradle séparés — la séparation logique suffit à démontrer la même rigueur sans le coût de build d'un multi-module pour une app de cette taille :

```
domain/          — modèles + interfaces de repository + use cases. Aucune dépendance Android
                   (Context, PackageManager…), donc testable en JVM pur, sans Robolectric.
data/            — implémentations Android des interfaces domain (PackageManager, DataStore,
                   Intent). Seule couche qui touche réellement la plateforme.
di/              — câblage Hilt : bind chaque interface domain à son implémentation data.
presentation/    — HomeViewModel (Hilt), HomeUiState.
ui/              — Composables (HomeScreen, ClockDisplay, FavoritesRow, SearchField,
                   AppListItem, AppPickerSheet) + thème Material 3.
```

Dépendance à sens unique : `presentation → domain ← data`. `presentation` et `data` ne se connaissent jamais directement. Le détail des décisions et de leurs compromis est documenté dans [`docs/adr`](docs/adr).

## Process de conception

Ce projet a été conçu avec Claude Code en mode agentique : au lieu de partir directement sur du code, la phase de planification a été itérée avec des allers-retours explicites — clarification du besoin produit, choix de stack (natif vs React Native, tranché en faveur du natif puisqu'iOS est hors scope), puis, une fois l'objectif de démonstration de compétences précisé, remise en question et durcissement du plan initial (architecture en couches, Hilt, tests dès la v1, documentation ADR) avant d'écrire la moindre ligne de code. Le plan approuvé est conservé tel quel dans l'historique de la session ; les ADRs ci-dessous en sont le résumé exploitable.

## Build & run

```bash
./gradlew testDebugUnitTest   # tests unitaires (use cases + ViewModel)
./gradlew installDebug        # installe l'app en debug sur un émulateur/device connecté
```

Après installation, appuyer sur le bouton Home affiche le dialogue de sélection de launcher Android — choisir l'app puis "Toujours". Pour un cycle de dev plus rapide (éviter le dialogue à chaque install) :

```bash
adb shell cmd package set-home-activity com.benjaminmichel.launcher/.MainActivity
```
