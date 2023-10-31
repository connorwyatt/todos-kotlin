plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(project(":common"))

    implementation(common.optional)
    implementation(common.rabbitmq)

    implementation(libraries.kodein.di)
    implementation(libraries.kotlinx.serialization.json)
}
