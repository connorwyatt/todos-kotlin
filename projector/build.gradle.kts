plugins { id("org.jetbrains.kotlin.jvm") }

dependencies {
    implementation(project(":data"))
    implementation(project(":domain:events"))

    implementation(common.data)
    implementation(common.eventstore)
    implementation(common.optional)

    implementation(libraries.kodein.di)
}
