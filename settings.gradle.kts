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
        //maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        //maven("https://jitpack.io")
    }
}

rootProject.name = "KPayPal"
include("kpaypal-api")
