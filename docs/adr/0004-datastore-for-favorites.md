# 0004 — DataStore Preferences pour les favoris

## Contexte

Les 5 favoris n'ont besoin de persister qu'une association `slot index -> package name`. Room (avec une vraie table, un DAO, des migrations) et DataStore Preferences (clé/valeur) ont été considérés.

## Décision

Jetpack DataStore Preferences, avec 5 clés dédiées (`favorite_slot_0` … `favorite_slot_4`) plutôt qu'une collection unique, pour préserver l'identité de chaque slot.

## Conséquences

- Room serait une sur-ingénierie pour 5 valeurs sans relations ni requêtes complexes — pas de schéma à migrer, pas de DAO à maintenir.
- DataStore expose nativement un `Flow`, ce qui s'intègre directement dans `ObserveFavoriteAppsUseCase` sans couche d'observation supplémentaire.
- Limite acceptée : si la v1 évoluait vers un nombre de favoris variable (plus de 5, réordonnables), ce schéma à clés fixes ne tiendrait pas et il faudrait migrer vers une structure plus riche (JSON sérialisé, ou Room). Non bloquant pour le scope actuel, qui fixe le nombre de favoris à 5.
