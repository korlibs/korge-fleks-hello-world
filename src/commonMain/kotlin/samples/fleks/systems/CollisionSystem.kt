package samples.fleks.systems

import com.github.quillraven.fleks.*
import com.github.quillraven.fleks.World.Companion.family
import korlibs.korge.fleks.components.MotionComponent
import korlibs.korge.fleks.components.PositionComponent
import samples.fleks.components.DestructComponent
import samples.fleks.components.ImpulseComponent

class CollisionSystem : IteratingSystem(
    family { all(PositionComponent, MotionComponent) },
    interval = EachFrame
) {
    override fun onTickEntity(entity: Entity) {
        val pos = entity[PositionComponent]
        val motion = entity[MotionComponent]

        // To make collision detection easy we check here just the Y position if it is below 200 which means
        // that the object is colliding - In real games here is a more sophisticated collision check necessary ;-)
        if (pos.y > 200f) {
            pos.y = 200f
            // Check if entity has a Destruct or Impulse component
            if (entity has DestructComponent) {
                // Delegate "destruction" of the entity to the DestructSystem - it will destroy the entity after some other task are done
                entity[DestructComponent].triggerDestruction = true
            } else if (entity has ImpulseComponent) {
                // Do not destruct entity but let it bounce on the surface
                motion.velocityX  = motion.velocityX * 0.7f
                motion.velocityY = -motion.velocityY * 0.9f
            } else {
                // Entity gets destroyed immediately
                world -= entity
            }
        }
    }
}
