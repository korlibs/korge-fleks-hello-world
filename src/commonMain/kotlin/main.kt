import korlibs.korge.scene.sceneContainer
import korlibs.image.color.Colors
import korlibs.korge.Korge
import korlibs.korge.fleks.gameState.*
import korlibs.korge.fleks.utils.*
import korlibs.math.geom.Size
import korlibs.time.*
import kotlinx.serialization.modules.*
import samples.fleks.scenes.*

const val zoom = 2f // zoom factor for desktop
const val JVM_TARGET_VIRTUAL_WIDTH = 384  // == 1/5 FHD Display size
const val JVM_TARGET_VIRTUAL_HEIGHT = 216

suspend fun main() = Korge(

// Set windowSize for desktop or fullscreen for mobile/android
    windowSize = Size(
        width = JVM_TARGET_VIRTUAL_WIDTH * zoom,
        height = JVM_TARGET_VIRTUAL_HEIGHT * zoom
    ),
//    fullscreen = true,

    backgroundColor = Colors.BLACK,
    title = AppConfig.TITLE
) {
    AppConfig.deltaPerFrame = 1.0 / views.gameWindow.timePerFrame.milliseconds

    // Get screen dimensions (on android needed!), calculate zoom factor and adjust virtual width to fit
    // the entire screen width (this let us use the full screen on android devices)
    val targetVirtualHeight = AppConfig.MIN_VIRTUAL_HEIGHT
    val targetVirtualZoom = (gameWindow.height.toDouble() / AppConfig.MIN_VIRTUAL_HEIGHT.toDouble())
    val targetVirtualWidth = (gameWindow.width.toDouble() / targetVirtualZoom).toInt()

    println("target physical size: (WxH) ${gameWindow.width} x ${gameWindow.height}")
    println("virtual window size: (WxH) $targetVirtualWidth x $targetVirtualHeight (zoom: $targetVirtualZoom)")
    views.setVirtualSize(targetVirtualWidth, targetVirtualHeight)
    views.targetFps = 60.0

    // Adjust default virtual width value
    AppConfig.TARGET_VIRTUAL_WIDTH = targetVirtualWidth

    // Init Game state and load common assets before any scene is shown
    GameStateManager.initGameState()

    // Register additional own entity configs here
    GameStateManager.register(
        name = "myModule",
        module = SerializersModule {
            // Register additional own entity configs here which are loaded from LDtk level map config
            polymorphic(EntityConfig::class) {
//                subclass(ExplosionsEntityConfig::class)
            }
        }
    )
    sceneContainer().changeTo { GameScene() }
}
