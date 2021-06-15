import java.net.URI

plugins {
    kotlin("multiplatform") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.10"
    `maven-publish`
}

group = "net.lepinoid"
version = "1.0"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(LEGACY) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }

//    publishing {
//        jvm()
//        js()
//        mingwX64()
//        linuxX64()
//        val publicationsFromMainHost = listOf(jvm(), js()).map { it.name } + "kotlinMultiplatform"
//        publishing {
//            publications {
//                matching { it.name in publicationsFromMainHost }.all {
//                    val targetPublication = this@all
//                    tasks.withType<AbstractPublishToMaven>()
//                        .matching { it.publication == targetPublication }
//                        .configureEach { onlyIf { findProperty("isMainHost") == true } }
//                }
//            }
//        }
//    }
}



publishing {
    repositories.maven {
        name = "github"
        url = project.uri("https://maven.pkg.github.com/${System.getenv("GITHUB_REPOSITORY")}")
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
//    publications {
//        register<MavenPublication>("gpr") {
//            artifactId = project.base.archivesBaseName
//            from(components["kotlin"])
//        }
//    }
}