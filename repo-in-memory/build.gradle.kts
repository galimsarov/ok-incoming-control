val cache4kVersion: String by project
val coroutinesVersion: String by project

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":repo-tests"))
    implementation("io.github.reactivecircus.cache4k:cache4k:$cache4kVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}