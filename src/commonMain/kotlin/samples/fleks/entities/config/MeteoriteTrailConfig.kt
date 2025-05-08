package samples.fleks.entities.config


import com.github.quillraven.fleks.*
import korlibs.image.format.ImageAnimation
import korlibs.korge.fleks.components.MotionComponent
import korlibs.korge.fleks.components.RgbaComponent
import korlibs.korge.fleks.components.SpriteComponent
import korlibs.korge.fleks.entity.*
import korlibs.korge.fleks.tags.RenderLayerTag
import korlibs.korge.fleks.tags.ScreenCoordinatesTag
import korlibs.korge.fleks.utils.*
import kotlinx.serialization.*

@Serializable @SerialName("MeteoriteTrailConfig")
data class MeteoriteTrailConfig(
    override val name: String
) : EntityConfig {

    override fun World.entityConfigure(entity: Entity) : Entity {

        entity.configure {
            it += ScreenCoordinatesTag
            it += MotionComponent(
                velocityX = -30f + (-15..15).random(),
                velocityY = -100f + (-15..15).random()
            )
            it += SpriteComponent(
                name = "meteorite",
                animation = "FireTrail",
                running = true,
                direction = ImageAnimation.Direction.ONCE_FORWARD,
                destroyOnAnimationFinished = true
            )
            it += RgbaComponent()
            it += RenderLayerTag.MAIN_EFFECTS
//            it += RenderLayerTag.DEBUG
        }
        return entity
    }

    init {
        EntityFactory.register(this)
    }
}
