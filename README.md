# Korge-Fleks Hello World Template

This is a template for setting up a project with KorGe game engine and version 2.x of
[Fleks Entity Component System](https://github.com/Quillraven/Fleks) (ECS).
It uses gradle with kotlin-dsl. You can open this project in IntelliJ IDEA by opening the folder or
the build.gradle.kts file.

[Korge-Fleks](https://github.com/korlibs/korge-fleks) is maintained by [@jobe-m](https://github.com/jobe-m)

# Supported Versions

- Korge: `4.0.0-rc`
- Korge-fleks addon: `v0.0.4`
- Korge-parallax addon: `6cbac0f917f208ac1fe58dd3f0618af75f00427d` (on branch adaptation-of-parallax-view-to-korge-fleks)
- Korge-tiled addon: `13e674655b94d422839fd3b689f8ba40e92fa84c`
- Fleks: `c24925091ced418bf045ba0672734addaab573d8` (on branch 2.3-korge-serialization)

# Updating to newer versions of KorGE-Fleks

It is important to understand that Korge-Fleks depends on specific versions of Korge, Korge-parallax
addon, Korge-tiled addon and Fleks ECS.
Thus, updating the version of Korge-Fleks also involves updating all versions of those modules/addons.
Do not try to update only one version until you know what you are doing.

The current versions which are working together can be seen at the top of this readme in section
"Supported Versions".

The Korge, Fleks ECS and all Korge Addon versions need to be updated in following places:

## Korge version

Korge version needs to be updated in `gradle/libs.versions.toml`:

```kotlin
[plugins]
korge = { id = "com.soywiz.korge", version = "4.0.0-rc" }
```

## Fleks version

Fleks ECS version needs to be updated in the kproject file `libs/fleks.kproject.yml`:

```
[...]
src: git::Quillraven/Fleks::/src::2.3
```

## Korge Addon versions

All versions of used Korge addons (Korge-fleks, Korge-parallax, Korge-tiled) needs to be updated
in their corresponding kproject files `libs/korge-fleks.kproject.yml`, `libs/korge-parallax.kproject.yml` and
`libs/korge-tiled.kproject.yml`. It will look like below example:

```kotlin
[...]
src: git::korlibs/korge-xxx::/korge-xxx/src::v0.0.x
```

# More information

For more information how to compile and run this example please
continue to read the Korge Hello-World example
[README](https://github.com/korlibs/korge-hello-world/blob/main/README.md) file.

For more general information how to use Fleks ECS please have a look in the
[Fleks Wiki](https://github.com/Quillraven/Fleks/wiki). Make sure to read version 2.x there!

Checkout the Korge-Fleks [README](https://github.com/korlibs/korge-fleks/blob/main/README.md) to learn
which Korge-ready ECS systems and component configurations already exist which you can reuse!
