import java.lang.System.getenv

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.updateDeps)
    `maven-publish`
}

kotlin {
    withSourcesJar()
    jvmToolchain(JavaVersion.VERSION_17.majorVersion.toInt())
    jvm { }
    js(IR) {
        nodejs()
        binaries.executable()
    }
    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.coroutines)
                api(libs.kotlinx.datetime)
                api(libs.kotlinx.serialization.json)
                api(libs.kotlin.reflect)
                api(libs.kotlinx.uuid)
                implementation(libs.kotlin.logging)

                api(libs.ktor.serialization.json)

                api(libs.ktor.client.core)
                api(libs.ktor.client.content.negotiation)
            }
        }
        jvmMain {
            dependencies {
                api(libs.ktor.client.cio)
            }
        }
        jsMain {
            dependencies {
                api(libs.ktor.client.js)
            }
        }
    }
}

dokka {
    moduleName.set("KPayPal-Core")
}

publishing {
    publications {
        create<MavenPublication>("kpaypalCore") {
            from(components["kotlin"])
            pom {
                name.set("KPayPal-core")
                description.set("Idiomatic Kotlin Wrapper for The PayPal API")
                url.set("https://github.com/mani1232/KPayPal")

                licenses {
                    license {
                        name.set("GNU License")
                        url.set("https://github.com/mani1232/KPayPal/LICENSE")
                    }
                }

                developers {
                    developer {
                        id.set("mani1232")
                        name.set("mani1232")
                        email.set("support@worldmandia.cc")
                        organization.set("WorldMandia")
                        organizationUrl.set("https://worldmandia.cc")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/mani1232/KPayPal.git")
                    developerConnection.set("scm:git:ssh://github.com/KPayPal.git")
                    url.set("https://github.com/mani1232/KPayPal")
                }
            }
        }
    }
    publishing {
        repositories {
            maven {
                name = "WorldMandia"
                url = if (version.toString()
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
    }
}