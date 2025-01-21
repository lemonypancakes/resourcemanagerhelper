import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.5"
    id("org.danilopianini.publish-on-central") version "8.0.1"
}

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

tasks {
    withType<ShadowJar> {
        archiveClassifier = ""
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "org.danilopianini.publish-on-central")

    val major = 1
    val minor = 4
    val patch = 0
    val isSnapshot = false
    val baseVersion = "$major.$minor.$patch"
    group = "me.lemonypancakes.${rootProject.name}"
    version = if (isSnapshot) "$baseVersion-SNAPSHOT" else baseVersion

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

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                pom {
                    name = "ResourceManagerHelper"
                    description = "Gives access to Minecraft's resource manager."
                    url = "https://github.com/lemonypancakes/resourcemanagerhelper"
                    inceptionYear = "2023"

                    licenses {
                        license {
                            name = "GNU General Public License, Version 3.0"
                            url = "https://www.gnu.org/licenses/gpl-3.0.txt"
                        }
                    }

                    scm {
                        url = "https://github.com/lemonypancakes/${rootProject.name}"
                        connection = "scm:git://github.com:lemonypancakes/${rootProject.name}.git"
                        developerConnection = "scm:git://github.com:lemonypancakes/${rootProject.name}.git"
                    }

                    developers {
                        developer {
                            id = "lemonypancakes"
                            name = "Teofilo Jr. Daquipil"
                            url = "https://lemonypancakes.me"
                            email = "contact@lemonypancakes.me"
                            roles = listOf("developer", "maintainer")
                        }
                    }
                }
            }
        }
    }

    signing {
        useInMemoryPgpKeys(System.getenv("GPG_PRIVATE_KEY"), System.getenv("GPG_PASSPHRASE"))
    }
}
