plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "de.max_dev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // YouTube DataAPI v3
    implementation("com.google.api-client:google-api-client:1.23.0")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.23.0")
    implementation("com.google.apis:google-api-services-youtube:v3-rev222-1.25.0")
    // Spotify API Kotlin lib
    implementation("com.adamratzman:spotify-api-kotlin-core:3.8.8")
}

kotlin {
    jvmToolchain(16)
}

application {
    mainClass.set("MainKt")
}
