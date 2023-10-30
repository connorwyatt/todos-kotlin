plugins { id("org.jetbrains.kotlin.jvm") }

dependencies {
    implementation(project(":common"))

    implementation(common.eventStore)

    implementation(libraries.kodein.di)
    implementation(libraries.mongoDB.driver)
}
