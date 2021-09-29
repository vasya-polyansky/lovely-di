plugins {
    kotlin("multiplatform") version "1.5.10"
}

group = "dev.vp"
version = "0.1.0"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            tasks.withType<Test> {
                useJUnitPlatform()
            }

            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
            }
        }
    }

    targets.configureEach {
        compilations.configureEach {
            kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}
