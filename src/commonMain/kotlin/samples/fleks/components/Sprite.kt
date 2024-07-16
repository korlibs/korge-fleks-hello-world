package samples.fleks.components

import com.github.quillraven.fleks.*
import korlibs.korge.view.Container
import korlibs.korge.view.Image
import korlibs.korge.view.addTo
import korlibs.korge.view.animation.ImageAnimationView
import korlibs.image.bitmap.Bitmaps
import korlibs.image.format.ImageAnimation
import korlibs.korge.assetmanager.AssetStore

/**
 * The sprite component adds visible details to an entity. By adding sprite to an entity the entity will be
 * visible on the screen.
 */
data class Sprite(
    var imageData: String = "",
    var animation: String = "",
    var isPlaying: Boolean = false,
    var forwardDirection: Boolean = true,
    var loop: Boolean = false,
    // internal data
    var imageAnimView: ImageAnimationView<Image> = ImageAnimationView { Image(Bitmaps.transparent) }.apply { smoothing = false }
) : Component<Sprite> {
    override fun type(): ComponentType<Sprite> = Sprite

    override fun World.onAdd(entity: Entity) {
        val layerContainer = inject<Container>("layer0")

        val assetStore = inject<AssetStore>()
        // Set animation object
        val asset = assetStore.getImageData(imageData)
        imageAnimView.animation = asset.animationsByName.getOrElse(animation) { asset.defaultAnimation }
        imageAnimView.onPlayFinished = {
            // when animation finished playing trigger destruction of entity
            this -= entity
        }
        imageAnimView.addTo(layerContainer)
        // Set play status
        imageAnimView.direction = when {
            forwardDirection && !loop -> ImageAnimation.Direction.ONCE_FORWARD
            !forwardDirection && loop -> ImageAnimation.Direction.REVERSE
            !forwardDirection && !loop -> ImageAnimation.Direction.ONCE_REVERSE
            else -> ImageAnimation.Direction.FORWARD
        }
        if (isPlaying) { imageAnimView.play() }
    }

    override fun World.onRemove(entity: Entity) {
        imageAnimView.removeFromParent()
    }

    companion object : ComponentType<Sprite>()
}
