import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.shadow)
    alias(libs.plugins.minotaur)
}

val baseVersion = "0.0.1"
val commitHash = System.getenv("COMMIT_HASH")
val snapshotVersion = "${baseVersion}-dev.$commitHash"

group = "app.simplecloud.plugin"
version = if (commitHash != null) snapshotVersion else baseVersion

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
    maven {
        url = uri("https://libraries.minecraft.net")
    }
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.extendedclip.com/releases/")
    }
    maven("https://repo.simplecloud.app/snapshots")
    maven("https://buf.build/gen/maven")
}

dependencies {
    testImplementation(rootProject.libs.kotlinTest)
    implementation(rootProject.libs.kotlinJvm)

    compileOnly(rootProject.libs.paperApi)
    compileOnly(rootProject.libs.placeholderApi)
    implementation(rootProject.libs.bundles.simpleCloudController)
}

tasks.shadowJar {
    relocate("io.grpc", "app.simplecloud.relocate.grpc")
    relocate("app.simplecloud.controller", "app.simplecloud.relocate.controller")
    relocate("app.simplecloud.pubsub", "app.simplecloud.relocate.pubsub")
    relocate("app.simplecloud.droplet", "app.simplecloud.relocate.droplet")
    relocate("build.buf.gen", "app.simplecloud.relocate.buf")
    relocate("com.google.protobuf", "app.simplecloud.relocate.protobuf")
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "21"
}

tasks.named("shadowJar", ShadowJar::class) {
    mergeServiceFiles()
    archiveFileName.set("${project.name}.jar")
}

tasks.processResources {
    expand("version" to project.version,
        "name" to project.name)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

modrinth {
    token.set(project.findProperty("modrinthToken") as String? ?: System.getenv("MODRINTH_TOKEN"))
    projectId.set("U1XWdWNx")
    versionNumber.set(rootProject.version.toString())
    versionType.set("beta")
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
    )
    loaders.add("paper")
    loaders.add("purpur")
    changelog.set("https://docs.simplecloud.app/changelog")
    syncBodyFrom.set(rootProject.file("README.md").readText())
}
