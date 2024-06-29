import java.io.ByteArrayOutputStream

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.dokka)
    `maven-publish`
}

group = "cc.worldmandia"

application {
    mainClass.set("cc.worldmandia.PPApiExampleKt")
}

kotlin {
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
            name = "WorldMandiaRepositoryRelease"
            url = uri("https://repo.worldmandia.cc/releases")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
        maven {
            name = "WorldMandiaRepositorySnapshots"
            url = uri("https://repo.worldmandia.cc/snapshots")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("kpaypalApi") {
            groupId = group.toString()
            artifactId = "kpaypal-api"
            version = libraryVersion
            from(components["java"])
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