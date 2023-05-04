import korlibs.time.*
import korlibs.korge.*
import korlibs.korge.tween.*
import korlibs.korge.view.*
import korlibs.image.color.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.math.geom.*
import korlibs.math.interpolation.*
import com.github.quillraven.fleks.*
import com.github.quillraven.fleks.World.Companion.family

suspend fun main() = Korge(windowSize = Size(512, 512), backgroundColor = Colors["#2b2b2b"]) {
	val minDegrees = (-16).degrees
	val maxDegrees = (+16).degrees

	val image = image(resourcesVfs["korge.png"].readBitmap()) {
		rotation = maxDegrees
		anchor(.5, .5)
		scale(.8)
		position(256, 256)
	}

	val world = world {
		// Register all needed systems of the entity component system
		// The order of systems here also define the order in which the systems are called inside Fleks ECS
		systems {
			add(HelloWorldSystem())
		}
	}

	// Run the update of the Fleks ECS - this will periodically call all update functions of the systems (e.g. onTick(), onTickEntity(), etc.)
	addUpdater { dt ->
		world.update(dt.seconds.toFloat())
	}

	// Create entity for printing out "Hello World!"
	world.entity {
		it += HelloWorldComponent(
			text = "Hello World!"
		)
	}

	while (true) {
		image.tween(image::rotation[minDegrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
		image.tween(image::rotation[maxDegrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
	}
}

// Component to store "Hello World!" string for printing
data class HelloWorldComponent(
	var text: String = "",
	var counter: Int = 0
) : Component<HelloWorldComponent> {
	override fun type(): ComponentType<HelloWorldComponent> = HelloWorldComponent

	companion object : ComponentType<HelloWorldComponent>()
}

// System which will print out "Hello World!"
class HelloWorldSystem : IteratingSystem(
	family = family { all(HelloWorldComponent) }
) {
	override fun onTickEntity(entity: Entity) {
		val component = entity[HelloWorldComponent]

		// Write out "Hello World!" only once
		if (component.counter < 1) {
			println(component.text)
			component.counter++
		}
	}
}
