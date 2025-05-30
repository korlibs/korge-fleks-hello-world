package samples.fleks.systems

import com.github.quillraven.fleks.*
import com.github.quillraven.fleks.World.Companion.family
import korlibs.korge.fleks.components.PositionComponent
import samples.fleks.components.*

/**
 * A system which moves entities. It either takes the rididbody of an entity into account or if not
 * it moves the entity linear without caring about gravity.
 */
class MoveSystem : IteratingSystem(
    family {
        all(PositionComponent)  // Position component absolutely needed for movement of entity objects
            .any(PositionComponent, RigidbodyComponent)  // Rigidbody not necessarily needed for movement
    },
    interval = EachFrame
) {
    override fun onTickEntity(entity: Entity) {
        val pos = entity[PositionComponent]

        if (entity has RigidbodyComponent) {
            // Entity has a rigidbody - that means the movement will be calculated depending on it
            val rigidbody = entity[RigidbodyComponent]
            // Currently we just add gravity to the entity
//            pos.yAcceleration += rigidbody.mass * 9.81
            // TODO implement more sophisticated movement with rigidbody taking damping and friction into account
        }

//        pos.x += pos.xAcceleration * deltaTime
//        pos.y += pos.yAcceleration * deltaTime
    }
}
