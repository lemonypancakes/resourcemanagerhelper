import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("maven-publish")
    id("com.gradleup.shadow") version "8.3.5"
    id("io.github.patrick.remapper") version "1.4.2"
    id("com.diffplug.spotless") version "7.0.2"
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
    apply(plugin = "com.diffplug.spotless")

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
}

subprojects {
    apply(plugin = "io.github.patrick.remapper")
    apply(plugin = "com.gradleup.shadow")

    val minecraftVersion: String by project

    dependencies {
        if (project.name != "${rootProject.name}-api") compileOnly(project(":${rootProject.name}-api"))
        compileOnly("org.spigotmc:spigot:$minecraftVersion-R0.1-SNAPSHOT:remapped-mojang")
    }

    tasks {
        remap {
            inputTask.set(jar)
            version.set(minecraftVersion)
        }

        shadowJar {
            dependsOn(remap)
            archiveClassifier.set("remapped-mojang")
        }

        build {
            dependsOn(shadowJar)
        }
    }
}

tasks {
    register("remapped-mojang", ShadowJar::class) {
        dependsOn(subprojects.map { it.tasks.shadowJar })
        from(subprojects.map { it.tasks.jar.get().outputs.files })
        archiveClassifier.set("remapped-mojang")
    }

    shadowJar {
        dependsOn("remapped-mojang", subprojects.map { it.tasks.remap })
        archiveClassifier.set("")
    }

    build {
        dependsOn(shadowJar)
    }

    remap {
        isEnabled = false
    }
}
