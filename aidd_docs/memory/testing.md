# Testing

## Strategy

- Tests unitaires JVM uniquement : les use cases et le `HomeViewModel`. Pas de Robolectric.
- La couche `data` n'est pas testée : elle ne fait qu'adapter les API Android.
- `androidTest` (Compose UI test) est déclaré dans Gradle, mais aucun test n'est encore écrit.

## Tools

- JUnit 4.
- `kotlinx-coroutines-test` : `runTest`, avec `MainDispatcherRule` pour `Dispatchers.Main`.
- Turbine pour asserter les `Flow` et `StateFlow`.

## Conventions

- Les tests suivent l'arborescence des packages de `src/main`.
- Les fakes écrits à la main (`fakes/Fake*Repository`, `FakeAppLauncher`) remplacent les mocks : il n'y a aucune lib de mock.
- Le ViewModel est instancié directement avec de vrais use cases branchés sur des fakes, sans Hilt dans les tests.

## Run

- `./gradlew testDebugUnitTest`
