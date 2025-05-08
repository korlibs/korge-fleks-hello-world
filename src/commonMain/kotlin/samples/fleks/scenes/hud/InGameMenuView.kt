package samples.fleks.scenes.hud

import korlibs.event.*
import korlibs.korge.fleks.assets.*
import korlibs.korge.fleks.utils.AppConfig
import korlibs.korge.input.*
import korlibs.korge.view.*
import korlibs.korge.view.align.*
import korlibs.korge.view.animation.*
import korlibs.time.*
import kotlin.coroutines.*


inline fun Container.inGameMenuView(
    assetStore: AssetStore,
    coroutineContext: CoroutineContext,
    block: @ViewDslMarker InGameMenuView.() -> Unit = {}
) =
    InGameMenuView(assetStore, coroutineContext).addTo(this, block)
class InGameMenuView(
    assetStore: AssetStore,
    var coroutineContext: CoroutineContext
) : Container() {

    var onPauseAction: (newState: Boolean) -> Unit = {}
    var onRewindAction: (fast: Boolean) -> Unit = {}
    var onForwardAction: (fast: Boolean) -> Unit = {}
    var onSaveGameAction: (save: Boolean) -> Unit = {}

    private var newState: Boolean = true

    private val pauseButton = imageDataView(assetStore.getImageData("button_pause")) {
        x = AppConfig.TARGET_VIRTUAL_WIDTH * 0.9
        y = AppConfig.MIN_VIRTUAL_HEIGHT * 0.03
        smoothing = false

        onClick { handlePauseAction() }
    }

    private val fastForwardButton = touchButtonView(assetStore.getImageData("button_fast_forward")) {
        alignRightToLeftOf(pauseButton, 9)
        onPressed = { onForwardAction.invoke(true) }
    }

    private val forwardButton = touchButtonView(assetStore.getImageData("button_forward")) {
        alignRightToLeftOf(fastForwardButton, 3)
        onPressed = { onForwardAction.invoke(false) }
    }

    private val rewindButton = touchButtonView(assetStore.getImageData("button_rewind")) {
        alignRightToLeftOf(forwardButton, 3)
        onPressed = { onRewindAction.invoke(false) }
    }

    private fun handlePauseAction() {
        newState = !newState
        onPauseAction.invoke(newState)
    }

    init {
        touchButtonView(assetStore.getImageData("button_fast_rewind")) {
            alignRightToLeftOf(rewindButton, 3)
            onPressed = {
                onRewindAction.invoke(true)
            }
        }

        keys {
            // For keyboard navigation of in-game-menu features
            justDown(Key.P) { handlePauseAction() }  // Pause

            // TESTING - fast rewind or forward of recorded game states
            downFrame(Key.F1, dt = (1/30.0f).seconds) { onRewindAction.invoke(true) }
            downFrame(Key.F2, dt = (1/30.0f).seconds) { onRewindAction.invoke(false) }
            downFrame(Key.F3, dt = (1/30.0f).seconds) { onForwardAction.invoke(false) }
            downFrame(Key.F4, dt = (1/30.0f).seconds) { onForwardAction.invoke(true) }

            // TESTING - for manually saving or loading game states
            justDown(Key.L) { onSaveGameAction.invoke(false) }
            justDown(Key.S) { onSaveGameAction.invoke(true) }
        }
    }
}
