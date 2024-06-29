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
    jvmToolchain(JavaVersion.VERSION_21.ordinal + 1)
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
            version = findProperty("apiVersion").toString()
            from(components["java"])
        }
    }
}