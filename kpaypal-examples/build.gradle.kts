plugins {
    alias(libs.plugins.updateDeps)
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
}

application {
    mainClass.set("$group.PPApiExampleKt")
}

dependencies {
    implementation(libs.kotlin.logging)
    implementation(project(":kpaypal-api"))
}

kotlin {
    jvmToolchain(JavaVersion.VERSION_17.majorVersion.toInt())
}