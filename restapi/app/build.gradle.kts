plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("io.ktor.plugin")
}

group = "io.connorwyatt.todos.restapi.app"

version = "0.0.1"

application {
    mainClass.set("io.connorwyatt.todos.restapi.app.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":restapi:models"))

    implementation(libraries.logback.classic)
    implementation(libraries.kodein.di)
    implementation(libraries.kodein.di.framework.ktor.server)
    implementation(libraries.ktor.serialization.kotlinx.json)
    implementation(libraries.ktor.server.contentNegotiation)
    implementation(libraries.ktor.server.core)
    implementation(libraries.ktor.server.netty)

    testImplementation(project(":restapi:client"))

    testImplementation(libraries.ktor.client.contentNegotiation)
    testImplementation(libraries.ktor.client.core)
    testImplementation(testingLibraries.kotlin.test)
    testImplementation(testingLibraries.ktor.server.testHost)
    testImplementation(testingLibraries.strikt.core)
}