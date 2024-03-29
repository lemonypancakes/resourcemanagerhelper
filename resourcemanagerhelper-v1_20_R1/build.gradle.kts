plugins {
    id("io.typecraft.gradlesource.spigot") version "1.0.0"
}

dependencies {
    compileOnly(project(":resourcemanagerhelper-api"))
    compileOnly("org.spigotmc:spigot:1.20.1-R0.1-SNAPSHOT:remapped-mojang")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

spigotRemap {
    spigotVersion.set("1.20.1")
    sourceJarTask.set(tasks.jar)
}

tasks.test {
    useJUnitPlatform()
}
