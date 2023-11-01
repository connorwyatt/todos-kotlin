rootProject.name = "todos"

include("data")
include("domain")
include("domain:events")
include("messages:commands")
include("projector")
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
        maven { url = uri("https://jitpack.io") }
    }

    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    versionCatalogs {
        val commonVersion: String by settings
        val jUnitVersion: String by settings
        val logbackVersion: String by settings
        val logstashLogbackEncoderVersion: String by settings
        val kodeinVersion: String by settings
        val kotlinVersion: String by settings
        val kotlinxSerializationVersion: String by settings
        val ktorVersion: String by settings
        val striktVersion: String by settings

        create("common") {
            val groupId = "com.github.connorwyatt.common-kotlin"

            library("configuration", groupId, "configuration").version(commonVersion)
            library("data", groupId, "data").version(commonVersion)
            library("eventstore", groupId, "eventstore").version(commonVersion)
            library("eventstore-mongodbModels", groupId, "eventstore-mongodb-models").version(commonVersion)
            library("http", groupId, "http").version(commonVersion)
            library("mongodb", groupId, "mongodb").version(commonVersion)
            library("optional", groupId, "optional").version(commonVersion)
            library("rabbitmq", groupId, "rabbitmq").version(commonVersion)
            library("time", groupId, "time").version(commonVersion)
        }

        create("libraries") {
            library("logback-classic", "ch.qos.logback", "logback-classic").version(logbackVersion)
            library("logstash-logbackEncoder", "net.logstash.logback", "logstash-logback-encoder").version(logstashLogbackEncoderVersion)
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
            library("ktor-serialization-kotlinx-json", "io.ktor", "ktor-serialization-kotlinx-json").version(
                ktorVersion
            )
            library("ktor-server-callId","io.ktor", "ktor-server-call-id").version(ktorVersion)
            library("ktor-server-callLogging","io.ktor", "ktor-server-call-logging").version(ktorVersion)
            library("ktor-server-contentNegotiation", "io.ktor", "ktor-server-content-negotiation").version(
                ktorVersion
            )
            library("ktor-server-core", "io.ktor", "ktor-server-core").version(ktorVersion)
            library("ktor-server-netty", "io.ktor", "ktor-server-netty").version(ktorVersion)
            library("ktor-server-requestValidation", "io.ktor","ktor-server-request-validation").version(ktorVersion)
            library("ktor-server-statusPages", "io.ktor","ktor-server-status-pages").version(ktorVersion)
        }

        create("testingLibraries") {
            library("junit-jupiter", "org.junit.jupiter", "junit-jupiter").version(jUnitVersion)
            library("junit-jupiter-engine", "org.junit.jupiter", "junit-jupiter-engine").version(jUnitVersion)
            library("ktor-server-testHost", "io.ktor", "ktor-server-test-host").version(ktorVersion)
            library("strikt.core", "io.strikt", "strikt-core").version(striktVersion)
        }
    }
}
