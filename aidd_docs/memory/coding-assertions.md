# Coding Assertions

## Before commit

| Order | Command                        | Checks                            |
| ----- | ------------------------------ | --------------------------------- |
| 1     | `./gradlew testDebugUnitTest`  | compilation + tests unitaires JVM |

## Before push

| Order | Command                        | Checks                                 |
| ----- | ------------------------------ | -------------------------------------- |
| 1     | `./gradlew assembleDebug`      | build complet de l'APK (KSP/Hilt inclus) |

## Behavior

Si un correctif est nécessaire, lancer un agent par assertion en échec.
