plugins { id("org.jetbrains.kotlin.jvm") }

dependencies {
    implementation(project(":common"))

    implementation(libraries.kodein.di)
    implementation(libraries.mongoDB.driver)
}
