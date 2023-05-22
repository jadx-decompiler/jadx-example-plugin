import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`

    id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
    implementation("io.github.skylot:jadx-core:1.5.0-SNAPSHOT") {
        isChanging = true
    }

    testImplementation("ch.qos.logback:logback-classic:1.4.7")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")

    testRuntimeOnly("io.github.skylot:jadx-smali-input:1.5.0-SNAPSHOT") {
        isChanging = true
    }
}

repositories {
    mavenCentral()
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
    google()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

version = System.getenv("VERSION") ?: "dev"

tasks {
    withType(Test::class) {
        useJUnitPlatform()
    }
    val shadowJar = withType(ShadowJar::class) {
        archiveClassifier.set("")
        dependencies {
            // jadx-core can be safely excluded from result jar
            exclude(dependency("io.github.skylot:jadx-core"))
        }
    }

    // copy result jar into "build/dist" directory
    register<Copy>("dist") {
        dependsOn(shadowJar)
        dependsOn(withType(Jar::class))

        from(shadowJar)
        into("$buildDir/dist")
    }
}
