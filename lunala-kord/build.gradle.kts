plugins {
    kotlin("jvm")
}

group = "com.gabriel.lunala.project"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://dl.bintray.com/kordlib/Kord")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    api(project(":lunala-api"))
    api("com.gitlab.kordlib.kord:kord-core:0.5.11")

    api("io.github.cdimascio:java-dotenv:5.2.1")
    api("mysql:mysql-connector-java:8.0.20")

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.5")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.3.5")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}