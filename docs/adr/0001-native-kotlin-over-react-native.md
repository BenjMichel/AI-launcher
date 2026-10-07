# 0001 — Kotlin natif plutôt que React Native

## Contexte

Le projet vise à terme aussi bien Android qu'un possible iOS. React Native a été envisagé pour mutualiser l'UI entre les deux plateformes.

## Décision

Développer en Kotlin natif (Jetpack Compose) pour cette v1, exclusivement Android.

## Conséquences

- Un vrai launcher Android nécessite de déclarer l'activité avec la catégorie `HOME` et d'interroger `PackageManager` (liste des apps lançables, icônes, lancement par `Intent`) — des API Android bas niveau que React Native n'expose pas nativement. Même avec RN, ces parties auraient dû être écrites en modules natifs Kotlin, ce qui aurait annulé une bonne partie du bénéfice de mutualisation attendu.
- iOS ne permet de toute façon pas de remplacer l'écran d'accueil système sans jailbreak — il n'existe aucune API publique Apple pour ça. La promesse de "code partagé pour un vrai launcher iOS" ne tenait donc pas : au mieux, un futur "pseudo-launcher" iOS s'appuierait sur l'API Screen Time (`ManagedSettings`/`DeviceActivity`), qui n'a elle-même aucun équivalent côté Android — peu de code serait réellement partageable entre les deux.
- Le natif donne un accès direct aux API Android, un APK plus léger, et de meilleures performances au démarrage — important pour un écran qui doit s'afficher instantanément à chaque déverrouillage.
