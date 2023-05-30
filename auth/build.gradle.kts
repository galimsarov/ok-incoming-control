val coroutinesVersion: String by project
val datetimeVersion: String by project

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":common"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
}