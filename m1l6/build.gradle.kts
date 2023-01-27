val coroutinesVersion: String by project

plugins {
    kotlin("jvm")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    testImplementation(kotlin("test-junit"))
}

tasks.test {
    useJUnitPlatform()
}