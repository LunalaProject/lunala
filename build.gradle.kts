plugins {
    java
    kotlin("jvm") version "1.4.0" apply false
}

allprojects {
    plugins.apply("java")

    group = "com.gabriel.lunala.project"
    version = "1.0"

    repositories {
        mavenCentral()
        jcenter()

        maven("https://repo.spring.io/milestone")
        maven("https://dl.bintray.com/ekito/koin")
    }

}


configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}