plugins {
    kotlin("jvm") version "1.6.0"
    application
}

repositories {
    mavenCentral()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.3"
    }
}

application {
    mainClassName = "Day02Kt"
}