plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    `java-test-fixtures`
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage") // Remove JUnit 4 engine
    }
    testFixturesImplementation("org.testcontainers:testcontainers:1.19.0")
    testFixturesImplementation("org.testcontainers:junit-jupiter:1.19.0")
    testFixturesImplementation("org.testcontainers:postgresql:1.19.0")
}

tasks {
    bootJar {
        enabled = false
    }
    jar {
        enabled = true
    }
}