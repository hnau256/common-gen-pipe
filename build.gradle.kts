import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinMultiplatformPlugin

allprojects {
    repositories {
        mavenCentral()
        google()
        mavenLocal()
        maven("https://jitpack.io")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    group = "com.github.hnau256"
    version = "1.0.1"
}


plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.library) apply false
}

/*
subprojects {
    plugins.withType<com.android.build.gradle.LibraryPlugin>().configureEach {
        extensions.configure<LibraryExtension>("android") {
            namespace = "com.github.hnau256." + project.name.replace('-', '.')
            compileSdk = 35
            defaultConfig {
                minSdk = 24
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                }
            }
        }
    }

    plugins.withType<PublishingPlugin>().configureEach {
        extensions.configure<PublishingExtension>("publishing") {
            publications {
                configureEach {
                    if (this is MavenPublication) {
                        groupId = project.group as String
                        artifactId = project.name
                        version = project.version as String
                    }
                }
            }
        }
    }

    plugins.withType<KotlinMultiplatformPlugin>().configureEach {
        extensions.configure<KotlinMultiplatformExtension>("kotlin") {
            androidTarget {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
                publishLibraryVariants("release")
            }
        }
    }
}*/
