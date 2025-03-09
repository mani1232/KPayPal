plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.updateDeps)
    `maven-publish`
}

kotlin {
    withSourcesJar()
    jvmToolchain(JavaVersion.VERSION_17.majorVersion.toInt())
    jvm {}

    sourceSets {
        commonMain {
            dependencies {
                api(project(":kpaypal-api:kpaypal-core"))
            }
        }
    }
}

dokka {
    moduleName.set("KPayPal-Webhook")
}

publishing {
    publications {
        create<MavenPublication>("kpaypalWebhook") {
            from(components["kotlin"])
            pom {
                name.set("KPayPal-webhook")
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
}