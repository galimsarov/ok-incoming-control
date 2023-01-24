rootProject.name = "ok-incoming-control"

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
    }
}

include("m1l1")
include("m1l4")
include("m1l5")