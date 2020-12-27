import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm") version "1.4.21"
    `java-gradle-plugin`
    `maven-publish`
    id("org.cadixdev.licenser") version "0.5.0"
    id("org.jmailen.kotlinter") version "3.3.0"
}

group = "io.github.juuxel"
version = "0.1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withSourcesJar()
}

repositories {
    mavenCentral()

    maven {
        name = "Jitpack"
        url = uri("https://jitpack.io")

        content {
            includeGroup("com.github.Juuxel")
        }
    }

    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    api("com.github.Juuxel", "Ripple", "0.1.0")
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
