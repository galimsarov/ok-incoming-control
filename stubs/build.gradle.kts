plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(project(":common"))

    testImplementation(kotlin("test-junit"))
}