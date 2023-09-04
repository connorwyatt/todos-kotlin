rootProject.name = "todos"
include("common")
include("data")
include("domain")
include("domain:events")
include("restapi:app")
include("restapi:client")
include("restapi:models")

pluginManagement {
    val kotlinVersion: String by settings
    val ktorVersion: String by settings
    val spotlessVersion: String by settings

    repositories {
        gradlePluginPortal()
    }

    plugins {
        id("com.diffplug.spotless") version spotlessVersion
        id("org.jetbrains.kotlin.jvm") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.serialization") version kotlinVersion
        id("io.ktor.plugin") version ktorVersion
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }

    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    versionCatalogs {
        val eventStoreClientVersion: String by settings
        val logbackVersion: String by settings
        val kodeinVersion: String by settings
        val kotlinVersion: String by settings
        val kotlinxSerializationVersion: String by settings
        val ktorVersion: String by settings
        val striktVersion: String by settings

        create("libraries") {
            library("eventStore-client", "com.eventstore", "db-client-java").version(eventStoreClientVersion)
            library("logback-classic", "ch.qos.logback", "logback-classic").version(logbackVersion)
            library("kodein-di", "org.kodein.di", "kodein-di").version(kodeinVersion)
            library("kodein-di-framework-ktor-server", "org.kodein.di", "kodein-di-framework-ktor-server-jvm").version(kodeinVersion)
            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").version(
                kotlinVersion
            )
            library("kotlinx-serialization-json", "org.jetbrains.kotlinx", "kotlinx-serialization-json").version(
                kotlinxSerializationVersion
            )
            library("ktor-client-cio", "io.ktor", "ktor-client-cio").version(ktorVersion)
            library("ktor-client-contentNegotiation", "io.ktor", "ktor-client-content-negotiation").version(ktorVersion)
            library("ktor-client-core", "io.ktor", "ktor-client-core").version(ktorVersion)
            library("ktor-server-contentNegotiation", "io.ktor", "ktor-server-content-negotiation").version(
                ktorVersion
            )
            library("ktor-server-core", "io.ktor", "ktor-server-core").version(ktorVersion)
            library("ktor-server-netty", "io.ktor", "ktor-server-netty").version(ktorVersion)
            library("ktor-serialization-kotlinx-json", "io.ktor", "ktor-serialization-kotlinx-json").version(
                ktorVersion
            )
        }

        create("testingLibraries") {
            library("kotlin-test", "org.jetbrains.kotlin", "kotlin-test").version(kotlinVersion)
            library("ktor-server-testHost", "io.ktor", "ktor-server-test-host").version(ktorVersion)
            library("strikt.core", "io.strikt", "strikt-core").version(striktVersion)
        }
    }
}
