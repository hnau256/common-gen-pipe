allprojects {
    repositories {
        mavenCentral()
        google()
        mavenLocal()
        maven("https://jitpack.io")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    group = "com.github.hnau256.common-gen-pipe"
    version = "1.2.0"
}


plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
}