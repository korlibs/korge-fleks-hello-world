package samples.fleks.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

/**
 * This component is used to add an entity the behaviour to "bounce" at collision with the ground.
 */
data class ImpulseComponent(
    var xForce: Float = 0f,  // not used currently
    var yForce: Float = 0f
) : Component<ImpulseComponent> {
    override fun type(): ComponentType<ImpulseComponent> = ImpulseComponent
    companion object : ComponentType<ImpulseComponent>()
}

