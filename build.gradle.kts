import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.github.patrick.gradle.remapper.RemapTask

plugins {
    id("java")
    id("com.diffplug.spotless") version "7.0.2"
    id("io.github.patrick.remapper") version "1.4.2"
    id("com.gradleup.shadow") version "9.0.1"
    id("maven-publish")
}

val majorVersion: String by project
val minorVersion: String by project
val patchVersion: String by project
val baseVersion = "$majorVersion.$minorVersion.$patchVersion"
val isSnapshot = project.property("isSnapshot").toString().toBoolean()
val finalVersion = if (isSnapshot) "$baseVersion-SNAPSHOT" else baseVersion
val buildNumber = System.getenv("BUILD_NUMBER") ?: ""
val isJenkins = buildNumber.isNotEmpty()

dependencies {
    subprojects.forEach { implementation(project(":${it.name}")) }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "io.github.patrick.remapper")
    apply(plugin = "maven-publish")

    group = "me.lemonypancakes.${rootProject.name}"
    version = if (isJenkins && isSnapshot) "$finalVersion-b$buildNumber" else finalVersion

    repositories {
        maven("https://repo.codemc.io/repository/nms/")
        maven("https://libraries.minecraft.net/")
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        compileOnly("org.jetbrains:annotations:26.0.2")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
        withJavadocJar()
        withSourcesJar()
    }

    spotless {
        java {
            googleJavaFormat()
        }
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                version = finalVersion
            }
        }

        repositories {
            maven {
                url = uri("https://repo.codemc.io/repository/lemonypancakes/")

                credentials {
                    username = System.getenv("JENKINS_USERNAME")
                    password = System.getenv("JENKINS_PASSWORD")
                }
            }
        }
    }

    tasks {
        remap {
            isEnabled = false
        }
    }
}

subprojects {
    apply(plugin = "io.github.patrick.remapper")

    val minecraftVersion: String by project

    dependencies {
        if (project.name != "${rootProject.name}-api") compileOnly(project(":${rootProject.name}-api"))
        compileOnly("org.spigotmc:spigot:$minecraftVersion-R0.1-SNAPSHOT:remapped-mojang")
    }

    tasks {
        val remapSpigotMapped = register<RemapTask>("remapSpigotMapped") {
            inputTask.set(jar)
            version.set(minecraftVersion)
            action.set(RemapTask.Action.MOJANG_TO_SPIGOT)
            archiveClassifier.set("spigot-mapped")
        }

        val remapObfuscated = register<RemapTask>("remapObfuscated") {
            inputTask.set(jar)
            version.set(minecraftVersion)
            action.set(RemapTask.Action.MOJANG_TO_OBF)
            archiveClassifier.set("obfuscated")
        }

        build {
            dependsOn(remapSpigotMapped, remapObfuscated)
        }
    }
}

tasks {
    val shadowSpigotMapped = register<ShadowJar>("shadowSpigotMapped") {
        from(subprojects.map { it.tasks.named<RemapTask>("remapSpigotMapped").get().outputs.files })
        archiveClassifier.set("spigot-mapped")
    }

    val shadowObfuscated = register<ShadowJar>("shadowObfuscated") {
        from(subprojects.map { it.tasks.named<RemapTask>("remapObfuscated").get().outputs.files })
        archiveClassifier.set("obfuscated")
    }

    shadowJar {
        archiveClassifier.set("")
    }

    build {
        dependsOn(shadowSpigotMapped, shadowObfuscated, shadowJar)
    }
}
