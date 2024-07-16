package samples.fleks.entities

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.World
import korlibs.korge.fleks.components.*
import korlibs.korge.fleks.components.SpawnerComponent
import samples.fleks.components.*
import samples.fleks.utils.random


/**
 * This function creates a spawner entity which sits on top of the screen and
 * spawns the meteorite objects. The config for it contains:
 * - a [PositionShapeComponent] which set the position of the spawner area 10 pixels
 *   above the visible area.
 * - a [SpawnerComponent] which tells the system that the spawned
 *   meteorite objects itself are spawner objects. These are spawning the fire trails while
 *   moving downwards.
 */
fun World.createMeteoriteSpawner() : Entity {
    return entity {

        // TODO: Take Components from Korge-fleks into use

        it += PositionComponent(
            x = 100f,
            y = -10f  // 10 pixel above the visible area
        )

        it += SpawnerComponent(  // Config for spawned objects
            numberOfObjects = 1,                // The spawner will generate one object per second
            interval = 60,                      // 60 frames mean once per second
            timeVariation = 30,                 // bring a bit of variation in the interval, so the respawning will happen every 30 to 90 frames (0.5 to 1.5 seconds)
            positionVariation = 100f,
            entityConfig = "meteorite_object"
        )

//* TODO clean up below custom components and use components from Korge-fleks
        it += Position(  // Position of spawner
            x = 100.0,
            y = -10.0  // 10 pixel above the visible area
        )
        it += Spawner(
            numberOfObjects = 1,  // The spawner will generate one object per spawn trigger
            interval = 60,        // 60 frames mean once per second
            timeVariation = 30,   // bring a bit of variation in the interval, so the respawning will happen every 30 to 90 frames (0.5 to 1.5 seconds)
            // Spawner details for spawned objects (spawned objects do also spawn objects itself)
            spawnerNumberOfObjects = 5, // Enable spawning feature for spawned object
            spawnerInterval = 1,
            spawnerPositionVariationX = 5.0,
            spawnerPositionVariationY = 5.0,
            spawnerPositionAccelerationX = -30.0,
            spawnerPositionAccelerationY = -100.0,
            spawnerPositionAccelerationVariation = 15.0,
            spawnerSpriteImageData = "meteorite",  // "" - Disable sprite graphic for spawned object
            spawnerSpriteAnimation = "FireTrail",  // "FireTrail" - "TestNum"
            spawnerSpriteIsPlaying = true,
            // Set position details for spawned objects
            positionVariationX = 100.0,
            positionVariationY = 0.0,
            positionAccelerationX = 90.0,
            positionAccelerationY = 200.0,
            positionAccelerationVariation = 10.0,
            // Destruct info for spawned objects
            destruct = true
        )
// */
    }
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
            it += Sprite(  // Config for spawned object
                imageData = spawner.spriteImageData,
                animation = spawner.spriteAnimation,
                isPlaying = spawner.spriteIsPlaying,
                forwardDirection = spawner.spriteForwardDirection,
                loop = spawner.spriteLoop
            )
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
