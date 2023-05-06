import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project
val logbackVersion: String by project

fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

plugins {
    id("application")
    kotlin("jvm")
    kotlin("plugin.serialization")
}

application {
    mainClass.set("ru.otus.otuskotlin.incomingControl.ApplicationKt")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":api-v1-jackson"))
    implementation(project(":mappers-v1"))
    implementation(project(":biz"))
    implementation(project(":repo-in-memory"))
    implementation(project(":repo-stubs"))

    implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
    implementation(ktor("netty")) // "io.ktor:ktor-ktor-server-netty:$ktorVersion"

    // jackson
    implementation(ktor("jackson", "serialization")) // io.ktor:ktor-serialization-jackson
    implementation(ktor("content-negotiation")) // io.ktor:ktor-server-content-negotiation
    implementation(ktor("kotlinx-json", "serialization")) // io.ktor:ktor-serialization-kotlinx-json

    implementation(ktor("locations"))
    implementation(ktor("caching-headers"))
    implementation(ktor("call-logging"))
    implementation(ktor("auto-head-response"))
    implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
    implementation(ktor("default-headers")) // "io.ktor:ktor-cors:$ktorVersion"
    implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
    implementation(ktor("auto-head-response"))

    implementation(ktor("websockets")) // "io.ktor:ktor-websockets:$ktorVersion"
    implementation(ktor("auth")) // "io.ktor:ktor-auth:$ktorVersion"
    implementation(ktor("auth-jwt")) // "io.ktor:ktor-auth-jwt:$ktorVersion"

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // tests
    testImplementation(kotlin("test-junit"))
    implementation(ktor("test-host")) // "io.ktor:ktor-server-test-host:$ktorVersion"
    implementation(ktor("content-negotiation", prefix = "client-"))
    implementation(ktor("websockets", prefix = "client-"))
}