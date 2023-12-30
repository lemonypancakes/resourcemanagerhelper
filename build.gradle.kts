plugins {
    id("java")
}

group = "me.lemonypancakes.resourcemanagerhelper"
version = "1.0.0-SNAPSHOT"

dependencies {
    implementation(project(":resourcemanagerhelper-api"))
    implementation(project(":resourcemanagerhelper-v1_17_R1"))
}
