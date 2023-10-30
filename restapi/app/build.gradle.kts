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
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":domain:events"))
    implementation(project(":messages:commands"))
    implementation(project(":projector"))
    implementation(project(":restapi:models"))

    implementation(common.configuration)
    implementation(common.eventStore)
    implementation(common.eventStore.kodein)
    implementation(common.eventStore.kodein.ktor)
    implementation(common.optional)
    implementation(common.time)
    implementation(common.time.kodein)

    implementation(libraries.hoplite.core)
    implementation(libraries.hoplite.json)
    implementation(libraries.logback.classic)
    implementation(libraries.logstash.logbackEncoder)
    implementation(libraries.kodein.di)
    implementation(libraries.kodein.di.framework.ktor.server)
    implementation(libraries.ktor.serialization.kotlinx.json)
    implementation(libraries.ktor.server.callId)
    implementation(libraries.ktor.server.callLogging)
    implementation(libraries.ktor.server.contentNegotiation)
    implementation(libraries.ktor.server.core)
    implementation(libraries.ktor.server.netty)
    implementation(libraries.ktor.server.requestValidation)
    implementation(libraries.ktor.server.statusPages)

    testImplementation(project(":restapi:client"))

    testImplementation(common.optional)

    testImplementation(libraries.ktor.client.contentNegotiation)
    testImplementation(libraries.ktor.client.core)
    testImplementation(testingLibraries.jUnit.jupiter)
    testImplementation(testingLibraries.ktor.server.testHost)
    testImplementation(testingLibraries.strikt.core)

    testRuntimeOnly(testingLibraries.jUnit.jupiter.engine)
}
