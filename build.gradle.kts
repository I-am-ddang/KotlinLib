import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group = "kr.bins"
version = "undefined"



plugins {
    id("idea")
    kotlin("jvm") version "1.9.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}


repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {

    shadow ("org.jetbrains.kotlin:kotlin-stdlib")
    shadow ("org.jetbrains.kotlin:kotlin-reflect:1.9.10")
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")

}

tasks {
    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }
    createJar("create") {

        var dest = File("C:/Users/psych/Desktop/minecraft 2023")
        doLast {
            copy {
                from(archiveFile)
                into(dest)
            }
        }
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "16"
    }
}
fun TaskContainer.createJar(name: String, configuration: ShadowJar.() -> Unit) {
    create<ShadowJar>(name) {
        archiveBaseName.set(project.name.replace("Lib", ""))
        archiveVersion.set(kotlin.coreLibrariesVersion) // For bukkit plugin update
        from(sourceSets["main"].output)
        configurations = listOf(project.configurations.shadow.get().apply { isCanBeResolved = true })
        configuration()
    }
}