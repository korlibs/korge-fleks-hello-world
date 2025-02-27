package samples.fleks.scenes

import korlibs.korge.fleks.utils.AppConfig
import korlibs.korge.fleks.tags.RenderLayerTag.*
import com.github.quillraven.fleks.*
import korlibs.korge.fleks.entity.config.*
import korlibs.korge.fleks.gameState.*
import korlibs.korge.fleks.renderSystems.*
import korlibs.korge.fleks.systems.*
import korlibs.korge.fleks.utils.*
import korlibs.korge.input.*
import korlibs.korge.scene.Scene
import korlibs.korge.view.*
import korlibs.time.*
import kotlinx.serialization.modules.*
import samples.fleks.entities.config.GameStartConfig
import samples.fleks.scenes.hud.inGameMenuView
import kotlin.time.Duration.Companion.seconds


class GameScene : Scene() {
    private lateinit var gameWorld: World

    override suspend fun SContainer.sceneInit() {
        println("Game scene init")

        // Reduce "Korgw.GameWindow.TooManyCallbacks" log entries at startup
        views.gameWindow.coroutineDispatcher.maxAllocatedTimeForTasksPerFrame = 10.seconds

        // Let game state manager decide which assets needs to be loaded
        GameStateManager.loadAssets()

        // Write atlas to file for debugging
        //GameStateManager.assetStore.writeAtlasToFile()

        val estimatedNumberOfEntities = 1024

        // This is the world object of the entity component system (ECS)
        // It contains all ECS related system and component configuration
        gameWorld = configureWorld(entityCapacity = estimatedNumberOfEntities) {

            // Register external objects which are used by systems and in component and family hook functions
            injectables {
                add("AssetStore", GameStateManager.assetStore)
            }

            // Register family hooks which trigger actions when specific entities (combination of components) are created
            families {
            }

            // Register all needed systems of the entity component system
            // The order of systems here also define the order in which the systems are called inside Fleks ECS
            systems {
                add(TouchInputSystem())
                add(SpawnerSystem())
                add(EventSystem())

                // Tween engine system
                setupTweenEngineSystems()

                // Systems below depend on changes of above tween engine systems
                add(LifeCycleSystem())
                add(ParallaxSystem(worldToPixelRatio = AppConfig.WORLD_TO_PIXEL_RATIO))
                add(PositionSystem())
                add(SpriteLayersSystem())
                add(EntityLinkSystem())
                add(SpriteSystem())

                add(SoundSystem())
                add(CameraSystem(worldToPixelRatio = AppConfig.WORLD_TO_PIXEL_RATIO))

                add(SnapshotSerializerSystem(
                    SerializersModule {
                        // Register additional own data classes here
                        polymorphic(CloneableData::class) {
//                            subclass(MyData::class)
                        }
                        // Register additional own component classes here
                        polymorphic(Component::class) {
//                            subclass(MyComponent::class)
                        }
                        // Register additional own tags (components without properties) here
                        polymorphic(UniqueId::class) {
//                            subclass(MyTag::class)
                        }
                    }
                ))
            }
        }

        // Create camera entity
        // First create entity config for camera
        MainCameraConfig(name = "main_camera")
        // Then create camera entity from entity config
        gameWorld.createAndConfigureEntity(entityConfig = "main_camera")

        // Create start script entity - this is the first entity which will be created when the game starts in GameStateManager.startGame()
        GameStartConfig(name = "start_script")

        // Run the update of the Fleks ECS - this will periodically call all update functions of the systems
        // (e.g. onTick(), onTickEntity(), etc.)
        addUpdater { dt ->
            val deltaTime = dt.seconds.toFloat()
            gameWorld.update(deltaTime)
        }

        // Background layers
        levelMapRenderSystem(gameWorld, layerTag = BG_LEVELMAP)  // level map for static background
        parallaxRenderSystem(gameWorld, layerTag = BG_PARALLAX)

        // Main layers
        levelMapRenderSystem(gameWorld, layerTag = MAIN_LEVELMAP)  // level map "main" for where the player and all enemies, npc, etc. move
        objectRenderSystem(gameWorld, layerTag = MAIN_SPRITES)
        fastSpriteRenderSystem(gameWorld, layerTag = MAIN_EFFECTS)
        objectRenderSystem(gameWorld, layerTag = MAIN_FOREGROUND)

        // Foreground layers
        levelMapRenderSystem(gameWorld, layerTag = FG_LEVELMAP)  // level map "foreground" for stuff that overlaps the player sprite
        parallaxRenderSystem(gameWorld, layerTag = FG_PARALLAX)
        objectRenderSystem(gameWorld, layerTag = FG_DIALOGS)

        // Debug shape layers
        debugRenderSystem(gameWorld, layerTag = DEBUG)

        // Pass touch input to TouchInputSystem
        onDown { gameWorld.system<TouchInputSystem>().onTouchDown(it.currentPosLocal.x.toFloat(), it.currentPosLocal.y.toFloat()) }
        onMove { gameWorld.system<TouchInputSystem>().onTouchMove(it.currentPosLocal.x.toFloat(), it.currentPosLocal.y.toFloat()) }
        onUp { gameWorld.system<TouchInputSystem>().onTouchUp(it.currentPosLocal.x.toFloat(), it.currentPosLocal.y.toFloat()) }

        // Game menu
        inGameMenuView(GameStateManager.assetStore, coroutineContext) {
            onPauseAction = { gameWorld.system<SnapshotSerializerSystem>().triggerPause() }
            onRewindAction = { fast -> gameWorld.system<SnapshotSerializerSystem>().rewind(fast) }
            onForwardAction = { fast -> gameWorld.system<SnapshotSerializerSystem>().forward(fast) }
            onSaveGameAction = { saveGameState ->
                if (saveGameState) gameWorld.system<SnapshotSerializerSystem>().saveGameState(gameWorld, coroutineContext)
                else gameWorld.system<SnapshotSerializerSystem>().loadGameState(gameWorld, coroutineContext)
            }
        }
    }

    override suspend fun SContainer.sceneMain() {
        //println("Game scene started")
    }

    override suspend fun sceneAfterInit() {
        // DO NOT BLOCK. Called after the old scene has been destroyed and the transition has been completed.
        println("Game scene after init")
        // Trigger game state manager to start game
        GameStateManager.startGame(gameWorld)
    }
}
