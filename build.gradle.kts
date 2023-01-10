import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val javaVer: String by project

plugins {
    kotlin("jvm")
}

group = "ru.otus.otuskotlin.marketplace"
version = "1.0-SNAPSHOT"

subprojects {
    group = rootProject.group
    version = rootProject.version
    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = javaVer
    }
}