plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.10-R0.1-SNAPSHOT")

    implementation("com.mysql:mysql-connector-j:9.1.0")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.flywaydb:flyway-core:10.10.0")
    implementation("org.flywaydb:flyway-mysql:10.10.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.jar {
    enabled = false
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveBaseName.set("NebulaPlugin")
    archiveVersion.set("1.0.0")
    archiveClassifier.set("")

    mergeServiceFiles()

    from(sourceSets.main.get().output)

    configurations = listOf(project.configurations.runtimeClasspath.get())
}

tasks.build {
    dependsOn(tasks.named("shadowJar"))
}
