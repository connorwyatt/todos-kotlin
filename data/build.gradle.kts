plugins { id("org.jetbrains.kotlin.jvm") }

dependencies {
    implementation(common.data)
    implementation(common.eventstore)
    implementation(common.eventstore.mongodbModels)
    implementation(common.mongodb)

    implementation(libraries.kodein.di)
}
