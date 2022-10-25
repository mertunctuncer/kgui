plugins {
    java
    `maven-publish`
    kotlin("jvm") version "1.7.20"
}

group = "dev.peopo"
version = "1.1.3"
description = "An extension library for bukkit GUI's and Itemstacks."

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://libraries.minecraft.net/")
}

dependencies {
    compileOnly("com.mojang:authlib:1.5.21")
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.+")
    implementation("com.google.code.gson:gson:2.9.+")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }
    compileKotlin{
        kotlinOptions.jvmTarget = "1.8"
    }
    wrapper {
        gradleVersion = "7.4"
        distributionType = Wrapper.DistributionType.ALL
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "${project.group}"
            artifactId = rootProject.name
            version = "${project.version}"

            from(components["java"])
        }
    }
}