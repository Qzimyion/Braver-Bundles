plugins {
    id("com.github.johnrengelman.shadow") version("+")
    id("maven-publish")
}

architectury {
    common("fabric")
    common("neoforge")
    platformSetupLoomIde()
}

val mod_id: String by project
val fabric_loader_version: String by project

loom.accessWidenerPath.set(file("src/main/resources/${mod_id}.accesswidener"))

sourceSets.main.get().resources.srcDir("src/main/generated/resources")

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${fabric_loader_version}")
    modApi("me.shedaniel.cloth:cloth-config:${project.properties["cloth_config_version"]}")
}
