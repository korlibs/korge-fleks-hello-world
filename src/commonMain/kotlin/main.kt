import com.soywiz.klock.*
import com.soywiz.korge.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.interpolation.*
import com.github.quillraven.fleks.*

suspend fun main() = Korge(width = 512, height = 512, bgcolor = Colors["#2b2b2b"]) {
	val minDegrees = (-16).degrees
	val maxDegrees = (+16).degrees

	val image = image(resourcesVfs["korge.png"].readBitmap()) {
		rotation = maxDegrees
		anchor(.5, .5)
		scale(.8)
		position(256, 256)
	}

	data class HelloWorldComponent(
		var text: String = "",
	    var counter: Int = 0
	)

	class MySystem : IteratingSystem(
		allOfComponents = arrayOf(HelloWorldComponent::class)
	) {
		val helloWorldComponents = Inject.componentMapper<HelloWorldComponent>()

		override fun onTickEntity(entity: Entity) {
			val component = helloWorldComponents[entity]

			// Write out only once
			if (component.counter < 1) {
				println(component.text)
				component.counter++
			}
		}

	}

	val world = world {

		// Register all needed systems of the entity component system
		// The order of systems here also define the order in which the systems are called inside Fleks ECS
		systems {
			add(::MySystem)
		}

		// Register all needed components and its listeners (if needed)
		components {
			add(::HelloWorldComponent)
		}
	}

	// Run the update of the Fleks ECS - this will periodically call all update functions of the systems (e.g. onTick(), onTickEntity(), etc.)
	addUpdater { dt ->
		world.update(dt.seconds.toFloat())
	}

	val helloWorldEntity = world.entity {
		add<HelloWorldComponent> {
			text = "Hello World!"
		}
	}

	while (true) {
		image.tween(image::rotation[minDegrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
		image.tween(image::rotation[maxDegrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
	}
}