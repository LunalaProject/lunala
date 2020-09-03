plugins {
    kotlin("jvm")
}

group = "com.gabriel.lunala.project"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://repo.spring.io/milestone")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":lunala-api"))

    implementation("org.jetbrains.kotlin:kotlin-compiler:1.3.72")
    implementation("org.jetbrains.kotlin:kotlin-script-util:1.3.72")

    implementation("io.projectreactor:reactor-core:3.4.0-M1")

    implementation("net.dv8tion:JDA:4.2.0_171")

    implementation("io.github.cdimascio:java-dotenv:5.2.1")
    implementation("mysql:mysql-connector-java:8.0.20")

    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.5")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.3.5")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}