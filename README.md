# Pipe

Pipe is a **compile-time DI code generator** for Kotlin Multiplatform projects.  
It is **minimalistic, lightweight**, and designed to provide dependencies in a **tree-like structure** of consumers.  
No runtime magic, just generated code.

---

## Installation

Pipe is published via [JitPack](https://jitpack.io/#hnau256/common-gen-pipe).

Add JitPack to your root `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

### Using `libs.versions.toml`

```toml
[versions]
ksp = "2.2.0-2.0.2"
pipe = "1.1.1"

[plugins]
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }

[libraries]
pipe-annotations = { module = "com.github.hnau256.common-gen-pipe:annotations", version.ref = "pipe" }
pipe-processor = { module = "com.github.hnau256.common-gen-pipe:processor", version.ref = "pipe" }
```

### Multiplatform Gradle configuration

```kotlin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.ksp)
}

kotlin {
    sourceSets {
        commonMain {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
                implementation(libs.pipe.annotations)
            }
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.pipe.processor)
}

tasks.withType<KotlinCompile>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
```

### JVM-only example

```kotlin
plugins {
    alias(libs.plugins.ksp)
    kotlin("jvm") version "2.0.20"
}

dependencies {
    implementation(libs.pipe.annotations)
    ksp(libs.pipe.processor)
}
```

---

## Usage

Define a `Dependencies` interface inside your component class and annotate it with `@Pipe`.  
Dependencies can include:

1. **Properties** (`val`) – provided dependencies.  
2. **Functions** returning another `@Pipe` interface – child dependency graphs.  
   - Functions can accept parameters which are passed to the child graph.  
3. **Empty companion object** – triggers generation of an `impl` extension function to build implementations.

### Example

```kotlin
import hnau.pipe.annotations.Pipe

class RootComponent(
    dependencies: Dependencies,
) {

    @Pipe
    interface Dependencies {
        val number: Int
        val bool: Boolean

        fun child(
            string: String,
            bool: Boolean
        ): ChildComponent.Dependencies

        companion object
    }

    val child = ChildComponent(
        dependencies = dependencies.child(
            string = "foo",
            bool = !dependencies.bool,
        ),
    )
}

class ChildComponent(
    dependencies: Dependencies,
) {
    @Pipe
    interface Dependencies {
        val int: Int
        val string: String
        val bool: Boolean
    }
}

fun main() {
    val root = RootComponent(
        dependencies = RootComponent.Dependencies.impl(
            number = 1,
            bool = true,
        )
    )
}
```

### Generated code

```kotlin
data class RootComponentDependenciesImpl(
    override val number: Int,
    override val bool: Boolean,
) : RootComponent.Dependencies {
    override fun child(
        string: String,
        bool: Boolean,
    ): ChildComponent.Dependencies = ChildComponentDependenciesImpl(
        int = number,
        string = string,
        bool = bool,
    )
}

fun RootComponent.Dependencies.Companion.impl(
    number: Int,
    bool: Boolean,
): RootComponent.Dependencies = RootComponentDependenciesImpl(
    number = number,
    bool = bool,
)

data class ChildComponentDependenciesImpl(
    override val int: Int,
    override val string: String,
    override val bool: Boolean,
) : ChildComponent.Dependencies
```

---

## Why Pipe?

- **Compile-time only** – no reflection or runtime overhead.  
- **Lightweight** – generates plain Kotlin data classes and functions.  
- **Tree-structured DI** – naturally fits hierarchical dependency graphs.  
- **Multiplatform ready** – works with Kotlin Multiplatform projects.  

---
