package samples.fleks.entities.config


import com.github.quillraven.fleks.*
import korlibs.korge.fleks.components.MotionComponent
import korlibs.korge.fleks.components.PositionComponent
import korlibs.korge.fleks.components.SpawnerComponent
import korlibs.korge.fleks.entity.*
import korlibs.korge.fleks.tags.RenderLayerTag
import korlibs.korge.fleks.tags.ScreenCoordinatesTag
import korlibs.korge.fleks.utils.*
import kotlinx.serialization.*

@Serializable @SerialName("MeteoriteConfig")
data class MeteoriteConfig(
    override val name: String
) : EntityConfig {

    override fun World.entityConfigure(entity: Entity) : Entity {

        entity.configure {
            // Entities -created by the SpawnerSystem- already have a position component
            it.getOrNull(PositionComponent)?.let { position ->
                // Add some vertical variation to the position of the spawned meteoroid objects
                position.x += (-100..100).random()
            }
            it += ScreenCoordinatesTag

            it += MotionComponent(
                velocityX = 90f + (-10..10).random(),
                velocityY = 200f + (-10..10).random(),
            )

            it += SpawnerComponent(
                numberOfObjects = 5,
                interval = 1,
                positionVariation = 5f,
                entityConfig = "meteorite_trails"
            )
/*
            // TODO: Destruct info for spawned objects
            destruct = true
 */

            //it += RenderLayerTag.DEBUG
        }

        return entity
    }

    init {
        EntityFactory.register(this)

        MeteoriteTrailConfig(
            name = "meteorite_trails"
        )
    }
}


/*




MovedSpawnerObject(
name = "meteorite_object",


numberOfObjects = 5,
interval = 1,
positionVariation = 5f,
entityConfig = "meteorite_dust",

velocityX = 90f,
velocityY = 200f,
velocityVariationX = 10f,
velocityVariationY = 10f
)
FireAndDustEffect(
name = "meteorite_dust",

assetName = "meteorite",
animationName = "FireTrail",
offsetX = 8f,
offsetY = 8f,
velocityX = -30f,
velocityY = -100f,
velocityVariationX = 15f,
velocityVariationY = 15f,
renderLayerTag = RenderLayerTag.MAIN_EFFECTS
)
}

/**
 *
 */
fun World.createMeteoriteObject(position: Position, spawner: Spawner) : Entity {
    return entity {
        var xx = position.x + spawner.positionX
        if (spawner.positionVariationX != 0.0) xx += (-spawner.positionVariationX..spawner.positionVariationX).random()
         var yy = position.y + spawner.positionY
        if (spawner.positionVariationY != 0.0) yy += (-spawner.positionVariationY..spawner.positionVariationY).random()
        var xAccel = spawner.positionAccelerationX
        var yAccel = spawner.positionAccelerationY
        if (spawner.positionAccelerationVariation != 0.0) {
            val variation = (-spawner.positionAccelerationVariation..spawner.positionAccelerationVariation).random()
            xAccel += variation
            yAccel += variation
        }

        it += Position(  // Position of spawner
            x = xx,
            y = yy,
            xAcceleration = xAccel,
            yAcceleration = yAccel
        )
        // Add spawner feature
        if (spawner.spawnerNumberOfObjects != 0) {
            it += Spawner(
                numberOfObjects = spawner.spawnerNumberOfObjects,
                interval = spawner.spawnerInterval,
                timeVariation = spawner.spawnerTimeVariation,
                // Position details for spawned objects
                positionX = spawner.spawnerPositionX,
                positionY = spawner.spawnerPositionY,
                positionVariationX = spawner.spawnerPositionVariationX,
                positionVariationY = spawner.spawnerPositionVariationY,
                positionAccelerationX = spawner.spawnerPositionAccelerationX,
                positionAccelerationY = spawner.spawnerPositionAccelerationY,
                positionAccelerationVariation = spawner.spawnerPositionAccelerationVariation,
                // Sprite animation details for spawned objects
                spriteImageData = spawner.spawnerSpriteImageData,
                spriteAnimation = spawner.spawnerSpriteAnimation,
                spriteIsPlaying = spawner.spawnerSpriteIsPlaying,
                spriteForwardDirection = spawner.spawnerSpriteForwardDirection,
                spriteLoop = spawner.spawnerSpriteLoop
            )
        }
        // Add sprite animations
        if (spawner.spriteImageData.isNotEmpty()) {
//            it += Sprite(  // Config for spawned object
//                imageData = spawner.spriteImageData,
//                animation = spawner.spriteAnimation,
//                isPlaying = spawner.spriteIsPlaying,
//                forwardDirection = spawner.spriteForwardDirection,
//                loop = spawner.spriteLoop
//            )
        }
        // Add destruct details
        if (spawner.destruct) {
            it += Destruct(
                spawnExplosion = true,
                explosionParticleRange = 15.0,
                explosionParticleAcceleration = 300.0
            )
        }
    }
}


 */
