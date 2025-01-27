import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.github.patrick.gradle.remapper.RemapTask

plugins {
    id("java")
    id("maven-publish")
    id("com.gradleup.shadow") version "8.3.5"
    id("io.github.patrick.remapper") version "1.4.2"
}

val majorVersion = project.property("majorVersion") as String
val minorVersion = project.property("minorVersion") as String
val patchVersion = project.property("patchVersion") as String
val baseVersion = "$majorVersion.$minorVersion.$patchVersion"
val isSnapshot = project.property("isSnapshot").toString().toBoolean()
val finalVersion = if (isSnapshot) "$baseVersion-SNAPSHOT" else baseVersion
val buildNumber = System.getenv("BUILD_NUMBER") ?: ""
val isJenkins = buildNumber.isNotEmpty()

dependencies {
    implementation(project(":${rootProject.name}-api"))
    implementation(project(":${rootProject.name}-v1_17_R1"))
    implementation(project(":${rootProject.name}-v1_18_R1"))
    implementation(project(":${rootProject.name}-v1_18_R2"))
    implementation(project(":${rootProject.name}-v1_19_R1"))
    implementation(project(":${rootProject.name}-v1_19_R2"))
    implementation(project(":${rootProject.name}-v1_19_R3"))
    implementation(project(":${rootProject.name}-v1_20_R1"))
    implementation(project(":${rootProject.name}-v1_20_R2"))
    implementation(project(":${rootProject.name}-v1_20_R3"))
    implementation(project(":${rootProject.name}-v1_21_R1"))
    implementation(project(":${rootProject.name}-v1_21_R2"))
    implementation(project(":${rootProject.name}-v1_21_R3"))
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    group = "me.lemonypancakes.${rootProject.name}"
    version = if (isJenkins && isSnapshot) "$finalVersion-b$buildNumber" else finalVersion

    repositories {
        mavenCentral()
        maven("https://repo.codemc.io/repository/nms/")
        maven("https://libraries.minecraft.net/")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21

        withJavadocJar()
        withSourcesJar()
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
}

subprojects {
    apply(plugin = "io.github.patrick.remapper")

    val mcVersion: String by project

    dependencies {
        if (project.name != "${rootProject.name}-api") {
            compileOnly(project(":${rootProject.name}-api"))
        }
        compileOnly("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT:remapped-mojang")
    }

    tasks {
        named<RemapTask>("remap") {
            version.set(mcVersion)
        }
    }
}

tasks {
    named<ShadowJar>("shadowJar") {
        dependsOn(subprojects.map { it.tasks.named("remap") })

        archiveClassifier.set("")
    }
}
