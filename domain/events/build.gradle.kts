plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(project(":common"))

    implementation(common.eventStore)
    implementation(common.optional)

    implementation(libraries.kotlinx.serialization.json)
}
