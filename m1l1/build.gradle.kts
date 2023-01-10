val kotlinVer: String by project

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVer")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVer")
}