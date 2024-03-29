import korlibs.korge.gradle.*

plugins {
	alias(libs.plugins.korge)
}

korge {
	id = "com.sample.korgeflekshelloworld"

// To enable all targets at once
	//targetAll()

// To enable targets based on properties/environment variables
	//targetDefault()

// To selectively enable targets
	targetJvm()
	targetJs()
//	targetIos()
	targetAndroid()

	serializationJson()

	this.autoGenerateTypedResources = false
}

dependencies {
	add("commonMainApi", project(":deps"))
}
