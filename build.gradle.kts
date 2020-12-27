import com.jfrog.bintray.gradle.BintrayExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm") version "1.3.72"
    `java-gradle-plugin`
    `maven-publish`
    id("org.cadixdev.licenser") version "0.5.0"
    id("org.jmailen.kotlinter") version "3.3.0"
    id("com.jfrog.bintray") version "1.8.5"
}

group = "io.github.juuxel"
version = "0.1.0"

if (file("private.gradle").exists()) {
    apply(from = "private.gradle")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withSourcesJar()
}

repositories {
    mavenCentral()

    maven {
        name = "JuuxelBintray"
        url = uri("https://dl.bintray.com/juuxel/maven")
    }

    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    api("io.github.juuxel", "ripple", "0.1.0")
    implementation("net.fabricmc", "lorenz-tiny", "3.0.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

gradlePlugin {
    plugins {
        create("ripplePlugin") {
            id = "io.github.juuxel.ripple"
            implementationClass = "juuxel.ripple.gradle.RipplePlugin"
        }
    }
}

license {
    header = file("HEADER.txt")
}

bintray {
    if (project.hasProperty("bintrayUser")) {
        user = project.property("bintrayUser").toString()
        key = project.property("bintrayKey").toString()
    } else {
        println("'bintrayUser' not found -- please set up 'bintrayUser' and 'bintrayKey' before publishing")
    }

    pkg(closureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = "ripple-gradle"
        setLicenses("MPL-2.0")
        vcsUrl = "https://github.com/Juuxel/RippleGradle"

        version(closureOf<BintrayExtension.VersionConfig> {
            name = project.version.toString()
        })
    })

    afterEvaluate {
        setPublications(*publishing.publications.map { it.name }.toTypedArray())
    }
}
