plugins {
    id("io.typecraft.gradlesource.spigot") version "1.0.0"
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.21.4-R0.1-SNAPSHOT:remapped-mojang")
}

spigotRemap {
    spigotVersion.set("1.21.4")
    sourceJarTask.set(tasks.jar)
}
