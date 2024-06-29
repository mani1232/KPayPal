import java.io.ByteArrayOutputStream
import java.lang.System.getenv

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.dokka)
    alias(libs.plugins.updateDeps)
    alias(libs.plugins.licenser)
    `maven-publish`
}

group = "cc.worldmandia"
rootProject.version = libraryVersion

application {
    mainClass.set("cc.worldmandia.PPApiExampleKt")
}

tasks {
    shadowJar {
        exclude("$group.examples")
    }
}

license {
    setHeader(file("HEADER.txt"))
}

kotlin {
    withSourcesJar()
    jvmToolchain(JavaVersion.VERSION_17.majorVersion.toInt())
    jvm {
        withJava()
    }
    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.coroutines)
                implementation(libs.other.klogging.slf4j)
                api(libs.kotlinx.serialization.json)
                api(libs.kotlin.reflect)

                api(libs.ktor.serialization.json)

                api(libs.ktor.client.core)
                api(libs.ktor.client.cio)
                api(libs.ktor.client.content.negotiation)
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
                username = getenv("MAVEN_NAME")
                password = getenv("MAVEN_SECRET")
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("kpaypalApi") {
            from(components["kotlin"])
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
        if (isRelease) snapshotPrefix else "$snapshotPrefix-SNAPSHOT"
    }

val Project.commitHash get() = git("rev-parse", "--verify", "HEAD")
val Project.shortCommitHash get() = git("rev-parse", "--short", "HEAD")

val Project.isRelease get() = tag != null