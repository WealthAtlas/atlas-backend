plugins {
    id("org.springframework.boot")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation(project(":user"))

    // Core Spring Boot test support (includes JUnit 5, Mockito, etc.)
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage") // Remove JUnit 4 engine
    }
}

tasks.test {
    useJUnitPlatform() // Ensures JUnit 5 (Jupiter) is used
}