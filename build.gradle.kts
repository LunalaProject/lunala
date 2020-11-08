plugins {
    kotlin("jvm") version "1.4.10"
}

allprojects {
    plugins.apply("kotlin")

    group = "com.gabriel.lunala.project"
    version = "1.0"

    repositories {
        jcenter()
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation("com.github.LunalaProject.wrapper:wrapper-jvm:0.5.10")

        implementation("org.slf4j:slf4j-api:1.7.25")
        implementation("ch.qos.logback:logback-classic:1.2.3")
        implementation("io.github.microutils:kotlin-logging:1.12.0")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0-M1")

        implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.0.0-RC")
    }

    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
}

