plugins { id("org.jetbrains.kotlin.jvm") }

dependencies {
    implementation(project(":common"))
    implementation(project(":domain:events"))

    implementation(common.eventStore)
    implementation(common.eventStore.kodein)

    implementation(libraries.kodein.di)
}
