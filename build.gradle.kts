import com.soywiz.korge.gradle.*

plugins {
	alias(libs.plugins.korge)
}

korge {
	id = "com.sample.demo"

	// Using Fleks standalone from maven central / local
	dependencyMulti("io.github.quillraven.fleks:Fleks:1.4-KMP-RC1", registerPlugin = false)

// To enable all targets at once

	targetAll()

// To enable targets based on properties/environment variables
	//targetDefault()

// To selectively enable targets
	
	targetJvm()
	targetJs()
	targetDesktop()
	targetIos()
	targetAndroidIndirect() // targetAndroidDirect()
	//targetAndroidDirect()
}
