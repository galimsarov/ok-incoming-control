plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(project(":common"))
    implementation(project(":stubs"))
    testImplementation(kotlin("test-junit"))
}