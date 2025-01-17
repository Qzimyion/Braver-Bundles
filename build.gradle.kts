import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    id("architectury-plugin") version("3.4-SNAPSHOT")
    id("dev.architectury.loom") version("+") apply(false)
    id("com.github.johnrengelman.shadow") version("+")
    id("maven-publish")
    id("eclipse")
    id("idea")
    id("java-library")
    id("java")
    id("org.ajoberstar.grgit") version("+")
    id("org.quiltmc.gradle.licenser") version("+")
    id("com.modrinth.minotaur") version("+")
    kotlin("jvm") version("2.0.21")
}

val mod_id: String by project
val mod_version: String by project
val mod_name: String by project
val mod_author: String by project
val mod_description: String by project
val credits: String by project
val mod_license: String by project
val java_version: String by project
val minecraft_version: String by project
val parchment_version: String by project
val fabric_loader_version: String by project
val fabric_api_version: String by project
val minecraft_version_range_fabric: String by project
val neoforge_version: String by project
val neoforge_loader_version_range: String by project
val minecraft_version_range_neoforge: String by project
val modmenu_version: String by project

architectury.minecraft = minecraft_version

val githubActions: Boolean = System.getenv("GITHUB_ACTIONS") == "true"
val licenseChecks: Boolean = githubActions

allprojects {
    version = mod_version
    group = project.properties["maven_group"] as String
}

subprojects {
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "architectury-plugin")
    apply(plugin = "maven-publish")
    apply(plugin = "org.quiltmc.gradle.licenser")

    base.archivesName.set(project.properties["archives_base_name"] as String + "-${project.name}")

    val loom = project.extensions.getByName<LoomGradleExtensionAPI>("loom")
    loom.silentMojangMappingsLicense()

    configurations {
        create("shadowBundle")
        "shadowBundle" {
            isCanBeResolved = true
            isCanBeConsumed = false
        }
    }

    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://maven.parchmentmc.org")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.minecraftforge.net/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://jitpack.io")
        maven("https://api.modrinth.com/maven")
        maven("https://maven.shedaniel.me/")
        maven("https://api.modrinth.com/maven")
    }

    @Suppress("UnstableApiUsage")
    dependencies {
        "minecraft"("com.mojang:minecraft:$minecraft_version")
        "mappings"(loom.layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-1.21:${parchment_version}@zip")
        })

        compileOnly("org.jetbrains:annotations:24.1.0")
        annotationProcessor("org.projectlombok:lombok:1.18.34")?.let { compileOnly(it) }

        implementation("org.ow2.asm:asm-tree:9.7")
    }

    java {
        withSourcesJar()
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.withType<JavaCompile>().configureEach {
        options.release.set(21)
    }

    tasks {
        processResources {
            val properties = HashMap<String, Any>()
            properties["mod_id"] = mod_id
            properties["mod_version"] = mod_version
            properties["mod_name"] = mod_name
            properties["mod_description"] = mod_description
            properties["mod_author"] = mod_author
            properties["mod_license"] = mod_license
            properties["minecraft_version_range_fabric"] = minecraft_version_range_fabric
            properties["fabric_loader_version"] = fabric_loader_version
            properties["java_version"] = java_version

            filesMatching("fabric.mod.json") {
                expand(properties)
            }
        }

        processResources {
            val properties = HashMap<String, Any>()
            properties["mod_id"] = mod_id
            properties["mod_version"] = mod_version
            properties["mod_name"] = mod_name
            properties["mod_description"] = mod_description
            properties["mod_author"] = mod_author
            properties["credits"] = credits
            properties["mod_license"] = mod_license
            properties["minecraft_version_range_neoforge"] = minecraft_version_range_neoforge
            properties["neoforge_version"] = neoforge_version
            properties["neoforge_loader_version_range"] = neoforge_loader_version_range
            properties["java_version"] = java_version

            filesMatching("META-INF/neoforge.mods.toml") {
                expand(properties)
            }
        }

        license {
            if (licenseChecks) {
                rule(file("codeformat/QUILT_MODIFIED_HEADER"))
                rule(file("codeformat/HEADER"))

                include("**//*.java")
                include("**//*.kt")
            }
        }
    }
}
