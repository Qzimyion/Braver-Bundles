pluginManagement.repositories {
    maven("https://maven.fabricmc.net/")
    maven("https://maven.architectury.dev/")
    maven("https://maven.minecraftforge.net/")
    maven("https://maven.neoforged.net/releases/")
    maven("https://jitpack.io")
    mavenCentral()
    gradlePluginPortal()
}

plugins {
    id("com.gradle.develocity") version("3.18.1")
}

develocity.buildScan {
    termsOfUseUrl = "https://gradle.com/terms-of-service"
    termsOfUseAgree = "yes"
}

include("common")
include("fabric")
include("neoforge")

rootProject.name = "Qzimyion's Bundle Tweaks"
