plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(common.optional)
    implementation(common.time)

    implementation(libraries.kotlinx.serialization.json)
}
