# Korge-Fleks Hello World Template

This is a template for setting up a project with KorGE game engine and version 2.x of
[Fleks Entity Component System](https://github.com/Quillraven/Fleks) (ECS).
It uses gradle with kotlin-dsl. You can open this project in IntelliJ IDEA by opening the folder or
the `build.gradle.kts` file.

[Korge-Fleks](https://github.com/korlibs/korge-fleks) is maintained by [@jobe-m](https://github.com/jobe-m)

## Korge version

Korge version needs to be updated in `gradle/libs.versions.toml`:

```kotlin
[plugins]
korge = { id = "com.soywiz.korge", version = "4.0.0-rc4" }
```

## Fleks version

Fleks ECS version needs to be updated in the kproject file `deps.kproject.yml`:

```
dependencies:
- https://github.com/korlibs/korge-fleks/tree/02fed30e752cc14c71cecafbdee2882c95d99e64/korge-fleks
```

# More information

For more information how to compile and run this example please
continue to read the Korge Hello-World example
[README](https://github.com/korlibs/korge-hello-world/blob/main/README.md) file.

For more general information how to use Fleks ECS please have a look in the
[Fleks Wiki](https://github.com/Quillraven/Fleks/wiki). Make sure to read version 2.x there!

Checkout the Korge-Fleks [README](https://github.com/korlibs/korge-fleks/blob/main/README.md) to learn
which Korge-ready ECS systems and component configurations already exist which you can reuse!
