package samples.fleks.entities.config

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.World
import korlibs.korge.fleks.components.*
import korlibs.korge.fleks.components.SpawnerComponent


import korlibs.korge.fleks.entity.*
import korlibs.korge.fleks.tags.RenderLayerTag
import korlibs.korge.fleks.tags.ScreenCoordinatesTag
import korlibs.korge.fleks.utils.*
import kotlinx.serialization.*


/**
 * This entity configuration creates a spawner entity which sits on top of the screen and
 * spawns the meteoroid objects. The config for it contains:
 *
 * - a [PositionComponent] which set the position of the spawner entity 10 pixels
 *   above the visible area.
 * - a [ScreenCoordinatesTag] to tell the system that the position is in screen coordinates.
 * - a [SpawnerComponent] which tells the system to spawn meteoroid objects.
 *   The meteoroid objects itself are spawners which spawn fire trails while moving downwards.
 *   This config information is stored in the [MeteoriteConfig] object. We just need to tell the
 *   SpawnerSystem to use the entity config (MeteoroidConfig) for the spawned objects.
 * - (optional) a [RenderLayerTag.DEBUG] to show the spawner entity position on the screen for debug reasons.
 */
@Serializable @SerialName("MeteoritesSpawnerConfig")
data class MeteoritesSpawnerConfig(
    override val name: String
) : EntityConfig {

    override fun World.entityConfigure(entity: Entity) : Entity {

        entity.configure {
            // Position of the spawner on top of the screen - for that we need to tell the system that the position is in screen coordinates
            it += PositionComponent(
                x = 100f,
                y = -10f  // 10 pixel above the visible area
            )
            it += ScreenCoordinatesTag

            it += SpawnerComponent(
                numberOfObjects = 1,        // The spawner will generate one object per second
                interval = 60,              // 60 frames mean once per second
                timeVariation = 30,         // bring a bit of variation in the interval, so the spawning will happen every 30 to 90 frames (0.5 to 1.5 seconds)
                entityConfig = "meteorite"  // The name of the entity config for the spawned objects
            )
            //it += RenderLayerTag.DEBUG
        }
        return entity
    }

    init {
        EntityFactory.register(this)

        MeteoriteConfig(
            name = "meteorite"
        )
    }
}
