plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(common.eventstore)
    implementation(common.http)
    implementation(common.mongodb)
    implementation(common.rabbitmq)
    implementation(common.time)

    implementation(libraries.eventstore.client)
    implementation(libraries.kodein.di)
    implementation(libraries.kodein.di.framework.ktor.server)
    implementation(libraries.kotlin.reflect)
    implementation(libraries.kotlinx.serialization.json)
    implementation(libraries.ktor.client.cio)
    implementation(libraries.ktor.client.core)
    implementation(libraries.ktor.server.core)
    implementation(libraries.mongodb.driver)
    implementation(libraries.rabbitmq.client)

    testImplementation(testingLibraries.junit.jupiter)
    testImplementation(testingLibraries.strikt.core)

    testRuntimeOnly(testingLibraries.junit.jupiter.engine)
}
