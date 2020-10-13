plugins {
    kotlin("jvm") apply true
    kotlin("plugin.serialization") version "1.4.10" apply true
}

repositories {
    maven("https://dl.bintray.com/kordlib/Kord")
}

dependencies {
    api(project(":lunala-api"))

    implementation("com.gitlab.kordlib.kord:kord-core:0.6.6")
}
