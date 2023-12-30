plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.lemonypancakes.resourcemanagerhelper"
version = "1.0.0-SNAPSHOT"

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
