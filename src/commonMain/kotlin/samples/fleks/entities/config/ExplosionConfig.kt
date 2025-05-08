package samples.fleks.entities.config

import com.github.quillraven.fleks.World
import samples.fleks.components.*
import korlibs.korge.fleks.utils.*

import com.github.quillraven.fleks.*
import korlibs.korge.fleks.components.MotionComponent
import korlibs.korge.fleks.components.PositionComponent
import korlibs.korge.fleks.entity.*
import kotlinx.serialization.*

@Serializable @SerialName("ExplosionConfig")
data class ExplosionConfig(
    override val name: String
) : EntityConfig {

    override fun World.entityConfigure(entity: Entity) : Entity {

        entity.configure {
/*

*/
        }
        return entity
    }

    init {
        EntityFactory.register(this)
    }
}

fun World.createExplosionArtefact(position: PositionComponent, destruct: DestructComponent) {
    entity {
        // set initial position of explosion object to collision position
        var xx = position.x
        var yy = position.y - (destruct.explosionParticleRange * 0.5f)
        if (destruct.explosionParticleRange != 0f) {
            xx += (-destruct.explosionParticleRange..destruct.explosionParticleRange).random()
            yy += (-destruct.explosionParticleRange..destruct.explosionParticleRange).random()
        }
        // make sure that all spawned objects are above 200 - this is hardcoded for now since we only have some basic collision detection at y > 200
        // otherwise the explosion artefacts will be destroyed immediately and appear at position 0x0 for one frame
        if (yy > 200.0) { yy = 199f }

        it += PositionComponent(  // Position of explosion object
            // set initial position of explosion object to collision position
            x = xx,
            y = yy
        )
        it += MotionComponent(
//            velocityX =
//            xAcceleration = position.xAcceleration + random(destruct.explosionParticleAcceleration),
//            yAcceleration = -position.yAcceleration + random(destruct.explosionParticleAcceleration)
        )
//        it += Sprite(
//            imageData = "meteorite",  // "" - Disable sprite graphic for spawned object
//            animation = "FireTrail",  // "FireTrail" - "TestNum"
//            isPlaying = true
//        )
        it += RigidbodyComponent(
            mass = 2.0
        )
        it += ImpulseComponent()
    }
}
