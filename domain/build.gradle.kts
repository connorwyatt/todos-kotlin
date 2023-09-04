plugins { id("org.jetbrains.kotlin.jvm") }

dependencies {
    implementation(project(":common"))
    implementation(project(":domain:events"))

    implementation(libraries.kodein.di)
}
