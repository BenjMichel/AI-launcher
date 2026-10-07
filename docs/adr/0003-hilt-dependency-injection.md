# 0003 — Hilt pour l'injection de dépendances

## Contexte

`HomeViewModel` dépend de cinq use cases, eux-mêmes adossés à des repositories dont l'implémentation touche des API Android (`Context`, `PackageManager`, DataStore). Il fallait un mécanisme de câblage : DI manuelle (un petit conteneur écrit à la main) ou Hilt.

## Décision

Hilt, avec un unique module (`di/RepositoryModule`) liant chaque interface `domain.repository.*` à son implémentation `data.*` via `@Binds`, installé dans `SingletonComponent`.

## Conséquences

- Standard officiel de l'écosystème Jetpack — signal de maîtrise de l'outillage Android moderne, cohérent avec l'objectif de vitrine technique du projet.
- Élimine le besoin de faire hériter `HomeViewModel` d'`AndroidViewModel` (qui aurait exigé de lui passer un `Application`/`Context`) : le ViewModel ne dépend que de use cases, donc reste instanciable directement dans les tests sans mock d'Android — un vrai gain de testabilité, pas seulement une case cochée.
- Coût : léger surcoût de build (annotation processing via KSP) et une couche d'indirection (`@Binds`) pour un graphe de dépendances qui, à cette taille, aurait aussi pu être câblé à la main en quelques lignes. Accepté en connaissance de cause pour la cohérence avec le reste de l'écosystème Android (Compose + `hiltViewModel()`).
