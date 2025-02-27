package samples.fleks.scenes.hud

import korlibs.image.format.*
import korlibs.korge.fleks.systems.*
import korlibs.korge.fleks.utils.AppConfig
import korlibs.korge.input.*
import korlibs.korge.view.*
import korlibs.korge.view.animation.*
import korlibs.time.*


inline fun Container.touchButtonView(
    data: ImageData? = null,
    block: @ViewDslMarker TouchButtonView.() -> Unit = {}
) = TouchButtonView(data).addTo(this, block)

class TouchButtonView(
    data: ImageData?
) : ImageDataView(data) {

    var onPressed: () -> Unit = {}

    init {
        y = AppConfig.MIN_VIRTUAL_HEIGHT * 0.03
        smoothing = false

        var pressed = false
        addFixedUpdater(snapshotFps.hz) { if (pressed) onPressed.invoke() }
        // Pressed state starts with touching button area "onDown"
        onDown { pressed = true }
        // and stops with un-touching button area "onUp" or moving touch out of button area "onOut"
        onUp { pressed = false }
        onOut { pressed = false }
    }
}
