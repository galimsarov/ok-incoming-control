val kotlinVersion: String by project

plugins {
    kotlin("jvm")
}

dependencies {
    testImplementation(kotlin("test-junit"))
}