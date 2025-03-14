package samples.fleks.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

/**
 * This is a very basic definition of a rigid body which does not take rotation into account.
 */
data class RigidbodyComponent(
    var mass: Double = 0.0,
    var velocityX: Double = 0.0,  // This and below are not yet used
    var velocityY: Double = 0.0,
    var damping: Double = 0.0,  // e.g. air resistance of the object when falling
    var friction: Double = 0.0,  // e.g. friction of the object when it moves over surfaces
) : Component<RigidbodyComponent> {
    override fun type(): ComponentType<RigidbodyComponent> = RigidbodyComponent
    companion object : ComponentType<RigidbodyComponent>()
}

