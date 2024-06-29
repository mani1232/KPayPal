import org.jetbrains.dokka.gradle.DokkaTask
import java.io.ByteArrayOutputStream
import java.lang.System.getenv

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.dokka)
    `maven-publish`
}

group = "cc.worldmandia"
rootProject.version = libraryVersion

application {
    mainClass.set("cc.worldmandia.PPApiExampleKt")
}

val dokkaOutputDir = "${layout.buildDirectory}/dokka"

tasks.getByName<DokkaTask>("dokkaHtml") {
    outputDirectory.set(file(dokkaOutputDir))
}

val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") {
    delete(dokkaOutputDir)
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    dependsOn(deleteDokkaOutputDir, tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaOutputDir)
}

kotlin {
    withSourcesJar()
    jvmToolchain(JavaVersion.VERSION_17.ordinal + 1)
    jvm {
        withJava()
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines)
                implementation(libs.other.klogging.slf4j)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlin.reflect)

                implementation(libs.ktor.serialization.json)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.content.negotiation)
            }
        }
    }
}

publishing {
    repositories {
        maven {
            name = "WorldMandiaRepository"
            url = if (rootProject.version.toString()
                    .endsWith("SNAPSHOT")
            ) uri("https://repo.worldmandia.cc/snapshots") else uri("https://repo.worldmandia.cc/releases")
            credentials {
                username = getenv("MAVENNAME")
                password = getenv("MAVENSECRET")
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("kpaypalApi") {
            artifact(javadocJar)
            groupId = group.toString()
            artifactId = "kpaypal-api"
            version = rootProject.version.toString()
            from(components["java"])
            pom {
                name.set("KPayPal")
                description.set("Idiomatic Kotlin Wrapper for The PayPal API")
                developers {
                    developer {
                        name.set("mani123")
                        email.set("support@worldmandia.cc")
                    }
                }
            }
        }
    }
}

internal fun Project.git(vararg command: String): String {
    val output = ByteArrayOutputStream()
    exec {
        commandLine("git", *command)
        standardOutput = output
        errorOutput = output
        workingDir = rootDir
    }.rethrowFailure().assertNormalExitValue()
    return output.toString().trim()
}

private val Project.tag
    get() = git("tag", "--no-column", "--points-at", "HEAD")
        .takeIf { it.isNotBlank() }
        ?.lines()
        ?.single()

val Project.libraryVersion
    get() = tag ?: run {
        val snapshotPrefix = when (val branch = git("branch", "--show-current")) {
            "master" -> providers.gradleProperty("nextPlannedApiVersion").get()
            else -> branch.replace('/', '-')
        }
        "$snapshotPrefix-SNAPSHOT"
    }

val Project.commitHash get() = git("rev-parse", "--verify", "HEAD")
val Project.shortCommitHash get() = git("rev-parse", "--short", "HEAD")

val Project.isRelease get() = tag != null