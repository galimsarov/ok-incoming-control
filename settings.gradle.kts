rootProject.name = "ok-incoming-control"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val ktorPluginVersion: String by settings
    val bmuschkoVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false

        id("io.ktor.plugin") version ktorPluginVersion apply false

        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
    }
}

//include("m1l1")
//include("m1l4")
//include("m1l5")
//include("m1l6")
//include("m2l3")

include("api-v1-jackson")

include("common")
include("mappers-v1")

include("stubs")
include("app-ktor")
include("app-kafka")
include("biz")