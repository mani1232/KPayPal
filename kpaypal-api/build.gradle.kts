import java.lang.System.getenv

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.updateDeps)
    `maven-publish`
}

group = "cc.worldmandia"

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
            version = rootProject.version.toString()
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