plugins {
    id("com.diffplug.spotless")
    id("org.jetbrains.kotlin.jvm") apply false
    id("org.jetbrains.kotlin.plugin.serialization") apply false
    id("io.ktor.plugin") apply false
}

tasks {
    create("installLocalGitHook") {
        delete {
            delete(File(rootDir, ".git/hooks/pre-commit"))
        }
        copy {
            from(File(rootDir, "scripts/pre-commit"))
            into(File(rootDir, ".git/hooks"))
            fileMode = 0b111101101
        }
    }

    build {
        dependsOn("installLocalGitHook")
    }
}

subprojects {
    apply(plugin = "com.diffplug.spotless")

    spotless {
        kotlin {
            target("**/*.kt")
            ktfmt(project.properties["ktfmtVersion"] as String).kotlinlangStyle()
        }
    }
}
