plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("io.arrow-kt:arrow-core:2.0.1")
}

tasks {
    bootJar {
        enabled = false
    }
    jar {
        enabled = true
    }
}