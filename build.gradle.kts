plugins {
    kotlin("jvm") version "1.8.20"
    application
}

group = "de.max_dev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {}

kotlin {
    jvmToolchain(16)
}

application {
    mainClass.set("Main")
}
