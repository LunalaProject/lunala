plugins {
    kotlin("jvm")
}

subprojects {
    plugins.apply("kotlin")

    group = "com.gabriel.lunala.project"
    version = "1.0-SNAPSHOT"

    dependencies {
        implementation(project(":lunala-api"))
        implementation(project(":lunala-jda"))
    }

    tasks {
        compileKotlin {
            kotlinOptions.jvmTarget = "1.8"
        }
        compileTestKotlin {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

}