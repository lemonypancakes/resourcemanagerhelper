import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.5"
    id("maven-publish")
}

group = "me.lemonypancakes.resourcemanagerhelper"
version = "1.4.0-SNAPSHOT"

dependencies {
    implementation(project(":resourcemanagerhelper-api"))
    implementation(project(":resourcemanagerhelper-v1_17_R1"))
    implementation(project(":resourcemanagerhelper-v1_18_R1"))
    implementation(project(":resourcemanagerhelper-v1_18_R2"))
    implementation(project(":resourcemanagerhelper-v1_19_R1"))
    implementation(project(":resourcemanagerhelper-v1_19_R2"))
    implementation(project(":resourcemanagerhelper-v1_19_R3"))
    implementation(project(":resourcemanagerhelper-v1_20_R1"))
    implementation(project(":resourcemanagerhelper-v1_20_R2"))
    implementation(project(":resourcemanagerhelper-v1_20_R3"))
    implementation(project(":resourcemanagerhelper-v1_21_R1"))
    implementation(project(":resourcemanagerhelper-v1_21_R2"))
    implementation(project(":resourcemanagerhelper-v1_21_R3"))
}

tasks {
    withType<ShadowJar> {
        archiveClassifier = ""
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    group = rootProject.group
    version = rootProject.version

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21

        withSourcesJar()
        withJavadocJar()
    }

    repositories {
        mavenCentral()
        maven("https://repo.codemc.io/repository/nms/")
        maven("https://libraries.minecraft.net/")
    }

    tasks {
        javadoc {
            options {
                encoding = "UTF-8"
            }
        }
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
            }
        }

        repositories {
            maven {
                url = uri("https://repo.codemc.io/repository/lemonypancakes")

                credentials {
                    username = System.getenv("JENKINS_USERNAME")
                    password = System.getenv("JENKINS_PASSWORD")
                }
            }
        }
    }
}
