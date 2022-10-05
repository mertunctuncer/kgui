

plugins {
    java
    `maven-publish`
    kotlin("jvm") version "1.7.20"

    id("io.papermc.paperweight.userdev") version "1.3.8"
}

group = "dev.peopo"
version = "1.0.0"
description = "An extension library for bukkit GUI's and Itemstacks."

repositories {
    mavenCentral()
}

dependencies {
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.+")
    implementation("com.google.code.gson:gson:2.9.+")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()

        options.release.set(17)
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