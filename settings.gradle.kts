rootProject.name = "ok-incoming-control"
include("m1l1")

pluginManagement {
    val kotlinVer: String by settings
    plugins {
        kotlin("jvm") version kotlinVer
    }
}