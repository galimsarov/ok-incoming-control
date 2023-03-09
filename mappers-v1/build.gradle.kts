plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(project(":api-v1-jackson"))
    implementation(project(":common"))

    testImplementation(kotlin("test-junit"))
}