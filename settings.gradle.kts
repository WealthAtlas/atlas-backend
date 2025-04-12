pluginManagement {
    plugins {
        kotlin("jvm") version "2.1.20" apply false
        kotlin("plugin.spring") version "2.1.20" apply false
        id("org.springframework.boot") version "3.2.0" apply false
        id("io.spring.dependency-management") version "1.1.4"
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "atlas-backend"

include("app")

