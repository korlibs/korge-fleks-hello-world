package samples.fleks

import korlibs.korge.scene.Scene
import korlibs.korge.view.container
import korlibs.korge.view.addUpdater
import com.github.quillraven.fleks.*
import korlibs.korge.assetmanager.AssetType
import korlibs.korge.assetmanager.loadAssets
import korlibs.korge.fleks.entity.config.FireAndDustEffect
import korlibs.korge.fleks.entity.config.FireAndDustEffect.configureEffectObject
import korlibs.korge.fleks.entity.config.MovedSpawnerObject
import korlibs.korge.fleks.familyHooks.*
import korlibs.korge.view.SContainer
import korlibs.time.seconds
import samples.fleks.systems.*
import samples.fleks.entities.createMeteoriteSpawner
import samples.fleks.entities.meteoriteDust
import samples.fleks.entities.meteoriteObject

class MainFleksSample : Scene() {
    companion object {
        const val scaleFactor = 3
    }

    override suspend fun SContainer.sceneInit() {

        // Configure and load the asset objects
        loadAssets(type = AssetType.Common, folderName = "common") {
            addImage(id = "meteorite", fileName = "sprites.ase")

            addEntityConfig(
                meteoriteObject.name,
                MovedSpawnerObject.Config(
                    numberOfObjects = 5,
                    interval = 1,
                    positionVariation = 5.0,
                    function = configureEffectObject,
                    config = meteoriteDust,
                    velocityX = 90.0,
                    velocityY = 200.0,
                    velocityVariationX = 10.0,
                    velocityVariationY = 10.0
                )
            )
            addEntityConfig(
                meteoriteDust.name,
                FireAndDustEffect.Config(
                    assetName = "meteorite",
                    animationName = "FireTrail",
                    offsetX = 8.0,
                    offsetY = 8.0,
                    velocityX = -30.0,
                    velocityY = -100.0,
                    velocityVariationX = 15.0,
                    velocityVariationY = 15.0,
                    drawOnLayer = "play_field_layer_5"
                )
            )
        }
    }

    override suspend fun SContainer.sceneMain() {
        container {
            scale = scaleFactor.toDouble()

            // Here are the container views which contain the generated entity objects with visible component "Sprite" attached to it
            //
            // TODO Build a more flexible views container system for handling layers for the SpriteSystem of Fleks ECS
            val layer0 = container()
            // val layer1 = container() // Add more layers when needed - This will be on top of layer0

            // This is the world object of the entity component system (ECS)
            // It contains all ECS related system and component configuration
            val world = configureWorld(entityCapacity = 512) {
                // Register external objects which are used by systems and component listeners
                injectables {
                    add("layer0", layer0)  // Currently, we use only one layer to draw all objects to - this is also used in SpriteListener to add the image to the layer container
                    // inject("layer1", layer1)  // Add more layers when needed e.g. for explosion objects to be on top, etc.
                }

                // Register family hooks which trigger actions when specific entities (combination of components) are created
                families {
                    // Register family hooks from Korge-fleks
                    onAdd(drawableFamily(), onDrawableFamilyAdded)
                    onRemove(drawableFamily(), onDrawableFamilyRemoved)
                    onAdd(specificLayerFamily(), onSpecificLayerFamilyAdded)
                    onRemove(specificLayerFamily(), onSpecificLayerFamilyRemoved)
                }

                // Register all needed systems of the entity component system
                // The order of systems here also define the order in which the systems are called inside Fleks ECS
                systems {
                    // TODO: Use systems provided by Korge-fleks
                    add(MoveSystem())
                    add(SpawnerSystem())
                    add(CollisionSystem())
                    add(DestructSystem())
                    add(SpriteSystem())   // Drawing images on screen should be last otherwise the position might be (0, 0) because it was not set before
                }
            }

            // Create an entity object which will spawn meteorites on top of the visual screen area
            world.createMeteoriteSpawner()

            // Run the update of the Fleks ECS - this will periodically call all update functions of the systems (e.g. onTick(), onTickEntity(), etc.)
            addUpdater { dt ->
                world.update(dt.seconds.toFloat())
            }
        }
    }
}
