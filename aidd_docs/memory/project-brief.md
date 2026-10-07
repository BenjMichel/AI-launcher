# Project Brief

## What it is

- Un launcher Android minimaliste (écran d'accueil `HOME`), inspiré d'Olauncher.
- Il sert aussi de vitrine de compétences en architecture Android : la rigueur de conception compte autant que la feature.

## Why it exists

- Faire de l'écran d'accueil un point de calme plutôt qu'une grille d'icônes.
- Vision long terme, hors scope v1 : un écran d'accueil assistant (météo, agenda). Ne pas l'anticiper dans le code actuel.
- iOS est hors scope, car Apple n'autorise pas le remplacement de l'écran d'accueil.

## Domain language

| Term | Meaning |
| ---- | ------- |
| favori / slot | l'une des 5 positions fixes de raccourci, indexées de `0` à `4` |
| picker | la bottom sheet (`AppPickerSheet`) qui assigne une app à un slot |
| app lançable | une activité qui répond à `ACTION_MAIN` + `CATEGORY_LAUNCHER`, sauf le launcher lui-même |

## Key features

- Une horloge.
- 5 favoris : un tap lance l'app ; un tap sur un slot vide ou un appui long ouvre le picker.
- Une recherche qui filtre en temps réel la liste des apps installées.
