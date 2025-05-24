plugins {
    kotlin("jvm")
    id("maven-publish")
}

tasks.create<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(kotlin.sourceSets.main.get().kotlin)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String
            version = project.version as String
            from(components["kotlin"])
            artifact(tasks["sourcesJar"])
        }
    }
}

dependencies {
    implementation(libs.ksp.api)
    implementation(libs.hnau.kotlin)
    implementation(libs.kotlinpoet.main)
    implementation(libs.kotlinpoet.ksp)
    implementation(libs.arrow.core)
    implementation(project(":annotations"))
}