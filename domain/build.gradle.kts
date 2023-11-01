plugins { id("org.jetbrains.kotlin.jvm") }

dependencies {
    implementation(project(":domain:events"))

    implementation(common.eventstore)
    implementation(common.optional)

    implementation(libraries.kodein.di)
}
