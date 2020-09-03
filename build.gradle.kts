plugins {
    java
    kotlin("jvm") version "1.3.72" apply false
}

allprojects {
    plugins.apply("java")

    group = "com.gabriel.lunala.project"
    version = "1.0"

    repositories {
        mavenCentral()
        jcenter()

        maven("https://dl.bintray.com/ekito/koin")
    }

}


configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}