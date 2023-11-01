plugins { id("org.jetbrains.kotlin.jvm") }

dependencies {
    implementation(project(":restapi:models"))

    implementation(common.http)

    implementation(libraries.kodein.di)
    implementation(libraries.ktor.client.contentNegotiation)
    implementation(libraries.ktor.client.core)
}
