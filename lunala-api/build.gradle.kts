plugins {
    kotlin("multiplatform")
}

group = "com.gabriel.lunala.project"
version = "1.0-SNAPSHOT"

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                api("ch.qos.logback:logback-classic:0.9.26")
                api("org.koin:koin-core:3.0.0-alpha-2")
            }
        }
        jvm().compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))

                api("com.zaxxer:HikariCP:3.4.2")

                api("org.jetbrains.exposed:exposed-core:0.24.1")
                api("org.jetbrains.exposed:exposed-jdbc:0.24.1")
                api("org.jetbrains.exposed:exposed-dao:0.24.1")
                api("org.jetbrains.exposed:exposed-java-time:0.24.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}
