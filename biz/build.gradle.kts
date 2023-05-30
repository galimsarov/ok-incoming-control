val coroutinesVersion: String by project

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":stubs"))
    implementation(project(":lib-cor"))
    implementation(project(":repo-tests"))
    implementation(project(":repo-stubs"))
    implementation(project(":auth"))
    implementation(project(":repo-in-memory"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    testImplementation(kotlin("test-junit"))
}