val datetimeVersion: String by project

plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    testImplementation(kotlin("test-junit"))
}