plugins {
    alias(libs.plugins.dokka) apply true
    `maven-publish`
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