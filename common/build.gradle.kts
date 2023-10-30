plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(common.eventStore)
    implementation(common.eventStore.kodein)
    implementation(common.time.kodein)

    implementation(libraries.eventStore.client)
    implementation(libraries.kodein.di)
    implementation(libraries.kodein.di.framework.ktor.server)
    implementation(libraries.kotlin.reflect)
    implementation(libraries.kotlinx.serialization.json)
    implementation(libraries.ktor.client.cio)
    implementation(libraries.ktor.client.core)
    implementation(libraries.ktor.server.core)
    implementation(libraries.mongoDB.driver)
    implementation(libraries.rabbitMQ.client)

    testImplementation(testingLibraries.jUnit.jupiter)
    testImplementation(testingLibraries.strikt.core)

    testRuntimeOnly(testingLibraries.jUnit.jupiter.engine)
}
