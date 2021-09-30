plugins {
    kotlin("multiplatform") version "1.5.31"
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka") version "1.5.30"
}

group = "io.github.vasya-polyansky"
version = "0.1.0"


val dokkaOutputDir = "$buildDir/dokka"

tasks.dokkaHtml {
    outputDirectory.set(file(dokkaOutputDir))
}

val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") {
    delete(dokkaOutputDir)
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    dependsOn(deleteDokkaOutputDir, tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaOutputDir)
}

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

    targets.all {
        compilations.all {
            kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}

signing {
    useInMemoryPgpKeys(
        System.getenv("GPG_PRIVATE_KEY")?.replace(" ", "\n"),
        System.getenv("GPG_PRIVATE_PASSWORD"),
    )
    sign(publishing.publications)
}

publishing {
    publications {
        repositories {
            maven {
                name = "OSSRH"
                setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2")
                credentials {
                    username = System.getenv("SONATYPE_USERNAME")
                    password = System.getenv("SONATYPE_PASSWORD")
                }
            }
        }

        withType<MavenPublication> {
            artifact(javadocJar)
            pom {
                name.set("lovely-di")
                url.set("https://github.com/vasya-polyansky/lovely-di")
                developers {
                    developer {
                        id.set("vasya-polyansky")
                        name.set("Vasya Polyansky")
                        email.set("vasya1polyansky@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:git@github.com:vasya-polyansky/lovely-di.git")
                    developerConnection.set("scm:git:git@github.com:vasya-polyansky/lovely-di.git")
                    url.set("https://github.com/vasya-polyansky/lovely-di.git")
                }
            }
        }
    }
}
