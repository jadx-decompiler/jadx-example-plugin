import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.util.Locale

plugins {
    `java-library`

    id("com.github.johnrengelman.shadow") version "8.1.1"

	// auto update dependencies with 'useLatestVersions' task
	id("se.patrikerdes.use-latest-versions") version "0.2.18"
	id("com.github.ben-manes.versions") version "0.52.0"
}

dependencies {
	val jadxVersion = "1.5.1"
	val isJadxSnapshot = jadxVersion.endsWith("-SNAPSHOT")

	// use compile only scope to exclude jadx-core and its dependencies from result jar
    compileOnly("io.github.skylot:jadx-core:$jadxVersion") {
        isChanging = isJadxSnapshot
    }

	testImplementation("io.github.skylot:jadx-smali-input:$jadxVersion") {
        isChanging = isJadxSnapshot
    }
	testImplementation("ch.qos.logback:logback-classic:1.5.18")
	testImplementation("org.assertj:assertj-core:3.27.3")
	testImplementation("org.junit.jupiter:junit-jupiter:5.12.1")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

repositories {
	mavenLocal()
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
        archiveClassifier.set("") // remove '-all' suffix
    }

    // copy result jar into "build/dist" directory
    register<Copy>("dist") {
		group = "jadx-plugin"
        dependsOn(shadowJar)
        dependsOn(withType(Jar::class))

        from(shadowJar)
        into(layout.buildDirectory.dir("dist"))
    }
}


tasks.named<DependencyUpdatesTask>("dependencyUpdates") {
	rejectVersionIf {
		// disallow release candidates as upgradable versions from stable versions
		isNonStable(candidate.version) && !isNonStable(currentVersion)
	}
}

fun isNonStable(version: String): Boolean {
	val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase(Locale.getDefault()).contains(it) }
	val regex = "^[0-9,.v-]+(-r)?$".toRegex()
	val isStable = stableKeyword || regex.matches(version)
	return isStable.not()
}
