pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
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
include("kpaypal-api:kpaypal-core")
findProject(":kpaypal-api:kpaypal-core")?.name = "kpaypal-core"
include("kpaypal-api:kpaypal-webhook")
findProject(":kpaypal-api:kpaypal-webhook")?.name = "kpaypal-webhook"
