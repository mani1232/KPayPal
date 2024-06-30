import java.io.ByteArrayOutputStream

plugins {
    alias(libs.plugins.updateDeps)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ktor)
}

rootProject.version = libraryVersion

application {
    mainClass.set("cc.worldmandia.PPApiExampleKt")
}

kotlin {
    jvmToolchain(JavaVersion.VERSION_17.majorVersion.toInt())
    jvm {
        withJava()
    }
    sourceSets {
        jvmMain {
            dependencies {
                implementation(libs.other.klogging.slf4j)
                implementation(project(":kpaypal-api"))
            }
        }
    }
}

private fun Project.git(vararg command: String): String {
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

val Project.isRelease get() = tag?.contains(version) ?: false