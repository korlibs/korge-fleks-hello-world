package samples.fleks.entities.config

import com.github.quillraven.fleks.*
import korlibs.korge.fleks.entity.*
import korlibs.korge.fleks.utils.*
import kotlinx.serialization.*

@Serializable @SerialName("GameStartConfig")
data class GameStartConfig (
    override val name: String
) : EntityConfig {

    override fun World.entityConfigure(entity: Entity) : Entity {

        // Create meteoroid spawner entity
        createAndConfigureEntity("meteorites_spawner")

        entity.configure {}
        return entity
    }

    init {
        EntityFactory.register(this)

        MeteoritesSpawnerConfig(
            name = "meteorites_spawner"
        )
    }
}