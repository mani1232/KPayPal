plugins {
    alias(libs.plugins.updateDeps)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ktor)
}
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

