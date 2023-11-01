plugins { id("org.jetbrains.kotlin.jvm") }

dependencies {
    implementation(project(":common"))

    implementation(common.eventstore)
    implementation(common.eventstore.mongodbModels)
    implementation(common.mongodb)

    implementation(libraries.kodein.di)
}
