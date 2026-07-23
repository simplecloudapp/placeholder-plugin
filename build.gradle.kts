import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.shadow)
    alias(libs.plugins.minotaur)
}

val baseVersion = "0.0.7"
val commitHash = System.getenv("COMMIT_HASH")
val snapshotVersion = "$baseVersion-dev.$commitHash"

group = "app.simplecloud.plugin"
version = if (commitHash != null) snapshotVersion else baseVersion

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/releases/")
    maven("https://repo.simplecloud.app/snapshots")
    maven("https://buf.build/gen/maven")
}

dependencies {
    compileOnly(rootProject.libs.kotlin.jvm)
    compileOnly(rootProject.libs.kotlin.coroutines.core)
    compileOnly(rootProject.libs.paper.api)
    compileOnly(rootProject.libs.placeholder.api)
    compileOnly(rootProject.libs.simplecloud)

    testImplementation(rootProject.libs.kotlin.test)
    testImplementation(rootProject.libs.kotlin.coroutines.core)
    testImplementation(rootProject.libs.simplecloud)
}

kotlin {
    jvmToolchain(25)
    compilerOptions {
        jvmTarget = JvmTarget.JVM_25
        languageVersion = KotlinVersion.KOTLIN_2_4
        apiVersion = KotlinVersion.KOTLIN_2_4
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    mergeServiceFiles()
    archiveFileName.set("${project.name}.jar")
}

tasks.processResources {
    expand(
        "version" to project.version
    )
}

modrinth {
    token.set(project.findProperty("modrinthToken") as String? ?: System.getenv("MODRINTH_TOKEN"))
    projectId.set("U1XWdWNx")
    versionNumber.set(rootProject.version.toString())
    versionType.set("release")
    uploadFile.set(tasks.shadowJar)
    gameVersions.addAll(
        "1.20",
        "1.20.1",
        "1.20.2",
        "1.20.3",
        "1.20.4",
        "1.20.5",
        "1.20.6",
        "1.21",
        "1.21.1",
        "1.21.2",
        "1.21.3",
        "1.21.4",
        "1.21.5",
        "1.21.6",
        "1.21.7",
        "1.21.8",
        "1.21.9",
        "1.21.10",
        "1.21.11",
        "26.1",
        "26.1.1",
        "26.1.2",
        "26.2",
    )
    loaders.addAll("paper", "purpur", "folia")
    changelog.set("https://docs.simplecloud.app/changelog")
    syncBodyFrom.set(rootProject.file("README.md").readText())
}