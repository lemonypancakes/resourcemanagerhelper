import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("maven-publish")
    id("signing")
}

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
}

tasks {
    withType<ShadowJar> {
        archiveClassifier = ""
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    group = "me.lemonypancakes.resourcemanagerhelper"
    version = "1.2.0"

    java {
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
                (this as CoreJavadocOptions).addStringOption("Xdoclint:none", "-quiet")
            }
        }
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])

                pom {
                    groupId = "me.lemonypancakes.resourcemanagerhelper"
                    name = "ResourceManagerHelper"
                    description = "Gives access to Minecraft's resource manager."
                    url = "https://github.com/lemonypancakes/resourcemanagerhelper"
                    inceptionYear = "2023"
                    packaging = "jar"

                    licenses {
                        license {
                            name = "GNU General Public License, Version 3.0"
                            url = "https://www.gnu.org/licenses/gpl-3.0.txt"
                            distribution = "repo"
                            comments = "A copyleft license that ensures software freedom"
                        }
                    }

                    scm {
                        url = "https://github.com/lemonypancakes/resourcemanagerhelper"
                        connection = "scm:git://github.com:lemonypancakes/resourcemanagerhelper.git"
                        developerConnection = "scm:git://github.com:lemonypancakes/resourcemanagerhelper.git"
                    }

                    developers {
                        developer {
                            id = "lemonypancakes"
                            name = "Teofilo Jr. V. Daquipil"
                            url = "https://lemonypancakes.me"
                            email = "JiboyJUNE@gmail.com"
                            roles = listOf("developer", "maintainer")
                        }
                    }
                }
            }
        }

        repositories {
            maven {
                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

                credentials {
                    username = project.findProperty("ossrhUsername")?.toString()
                    password = project.findProperty("ossrhPassword")?.toString()
                }
            }
        }
    }

    signing {
        sign(publishing.publications["mavenJava"])
    }
}
