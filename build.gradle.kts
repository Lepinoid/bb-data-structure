plugins {
    kotlin("multiplatform") version "1.9.25"
    kotlin("plugin.serialization") version "2.0.21"
    `maven-publish`
}

group = "net.lepinoid"
val build_number = System.getenv("GITHUB_RUN_NUMBER") ?: "local"
version = "build.$build_number"

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
            kotlinOptions.jvmTarget = "11"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled = true
                }
            }
            testTask {
                useMocha()
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
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
                implementation("com.benasher44:uuid:0.8.4")
                implementation("net.lepinoid:uuid-serializer:1.2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
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
    publications.create<MavenPublication>("mavenJava") {
        groupId = project.group.toString()
        artifactId = project.base.archivesName.get()
        version = project.version.toString()
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
    repositories {
        val targetPath = System.getenv("PUBLISH_PATH")
        if (targetPath != null) {
            maven {
                url = uri(targetPath)
            }
        }
    }
}
