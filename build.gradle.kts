import java.io.ByteArrayOutputStream

plugins {
    alias(libs.plugins.updateDeps) apply true
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.dokka) apply false
}

allprojects {
    group = "cc.worldmandia"
    version = libraryVersion
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
        if (isRelease == true) snapshotPrefix else "$snapshotPrefix-SNAPSHOT"
    }

val Project.commitHash get() = git("rev-parse", "--verify", "HEAD")
val Project.shortCommitHash get() = git("rev-parse", "--short", "HEAD")

val Project.isRelease
    get() = providers.gradleProperty("isRelease").get()
        .toBooleanStrictOrNull() // tag?.contains(providers.gradleProperty("nextPlannedApiVersion").get()) ?: false