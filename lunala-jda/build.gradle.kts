plugins {
    kotlin("jvm") apply true
    kotlin("plugin.serialization") version "1.4.0"
}

group = "com.gabriel.lunala.project"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://repo.spring.io/milestone")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    api(project(":lunala-api"))
    api("net.dv8tion:JDA:4.2.0_171")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")

    implementation("org.jetbrains.kotlin:kotlin-compiler:1.4.0")
    implementation("org.jetbrains.kotlin:kotlin-script-util:1.4.0")

    implementation("io.projectreactor:reactor-core:3.4.0-M1")

    implementation("io.github.cdimascio:java-dotenv:5.2.1")
    implementation("mysql:mysql-connector-java:8.0.20")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}