plugins {
    kotlin("jvm")
}

dependencies {
    val exposedVersion: String by project
    val postgresDriverVersion: String by project

    val testContainersVersion: String by project

    implementation(kotlin("stdlib"))

    implementation(project(":common"))

    implementation("org.postgresql:postgresql:$postgresDriverVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    testImplementation("org.testcontainers:postgresql:$testContainersVersion")
    testImplementation(project(":repo-tests"))
}