plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.updateDeps)
    `maven-publish`
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
                api(project(":kpaypal-api:kpaypal-core"))
            }
        }
    }
}

dokka {
    moduleName.set("KPayPal-webhook")
    dokkaSourceSets.all {
        includes.from("docs/INFO.md")
        sourceLink {
            localDirectory.set(file("src/main/kotlin"))
            remoteUrl("https://kpaypal.worldmandia.cc")
            remoteLineSuffix.set("#L")
        }
    }
    pluginsConfiguration.html {
        footerMessage.set("(c) WorldMandia")
    }
}