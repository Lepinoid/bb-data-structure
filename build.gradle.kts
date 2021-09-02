plugins {
    kotlin("multiplatform") version "1.5.30"
    kotlin("plugin.serialization") version "1.5.20"
    `maven-publish`
}

group = "net.lepinoid"
version = "4.1"

repositories {
    mavenCentral()
    maven {
        name = "lepinoid"
        url = uri("https://lepinoid.github.io/maven-repo/")
    }
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
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
                implementation("com.benasher44:uuid:0.3.0")
                implementation("net.lepinoid:uuid-serializer:1.0")
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

}


publishing {
//    repositories.maven {
//        name = "github"
//        url = project.uri("https://maven.pkg.github.com/${System.getenv("GITHUB_REPOSITORY")}")
//        credentials {
//            username = System.getenv("GITHUB_ACTOR")
//            password = System.getenv("GITHUB_TOKEN")
//        }
//    }
    publications.all {
        this as MavenPublication
        pom {
            name.set(project.name)
            url.set("https://github.com/Lepinoid/bb-data-structure")
            licenses {
                license {
                    name.set("MIT License")
                    url.set("https://github.com/Lepinoid/bb-data-structure/blob/main/LICENSE")
                }
            }
        }
    }
    repositories.maven {
        url = uri("${System.getProperty("user.home")}/lepinoid/maven-repo")
    }
}