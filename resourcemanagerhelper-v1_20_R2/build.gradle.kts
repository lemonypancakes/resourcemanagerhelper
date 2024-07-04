plugins {
    id("io.typecraft.gradlesource.spigot") version "1.0.0"
}

dependencies {
    compileOnly(project(":resourcemanagerhelper-api"))
    compileOnly("org.spigotmc:spigot:1.20.2-R0.1-SNAPSHOT:remapped-mojang")
}

spigotRemap {
    spigotVersion.set("1.20.2")
    sourceJarTask.set(tasks.jar)
}
