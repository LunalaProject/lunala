plugins {
    kotlin("kapt") apply true
    kotlin("plugin.serialization") version "1.4.10"
}

repositories {
    maven("https://dl.bintray.com/kordlib/Kord")
}

dependencies {
    implementation(project(":lunala-api"))

    implementation("com.gitlab.kordlib.kord:kord-core:0.6.6")

    implementation("org.yaml:snakeyaml:1.26")

    implementation("com.zaxxer:HikariCP:3.4.5")
    implementation("org.jetbrains.exposed:exposed-core:0.24.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.24.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.24.1")

    runtimeOnly("mysql:mysql-connector-java:8.0.20")
}
