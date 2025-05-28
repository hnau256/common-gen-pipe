allprojects {
    repositories {
        mavenCentral()
        google()
        mavenLocal()
        maven("https://jitpack.io")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    group = "com.github.hnau256"
    version = "1.0.5"
}


plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
}