pluginManagement { repositories { mavenLocal(); mavenCentral(); google(); gradlePluginPortal() } }

plugins {
    id("com.soywiz.kproject.settings") version "0.0.6" // "0.1.2-kotlin-1.7.21"
}

kproject("./deps")
