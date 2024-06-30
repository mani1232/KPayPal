pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.worldmandia.cc/snapshots")
    }
}

rootProject.name = "KPayPal"
include("kpaypal-api")
include("kpaypal-examples")
