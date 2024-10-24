import java.lang.System.getenv

plugins {
    alias(libs.plugins.dokka)
}

dependencies {
    dokka(project(":kpaypal-api:kpaypal-core"))
    dokka(project(":kpaypal-api:kpaypal-webhook"))
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
}

dokka {
    moduleName.set("KPayPal")
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