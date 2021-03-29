import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    val kotlinVersion: String by System.getProperties()
    id("kotlinx-serialization") version kotlinVersion
    kotlin("multiplatform") version kotlinVersion
}

version = "1.0.0-SNAPSHOT"
group = "ru.syntez"

repositories {
    mavenCentral()
    jcenter()
    mavenLocal()
}

// Versions
val kotlinVersion: String by System.getProperties()
val logbackVersion: String by project

kotlin {

    jvm("backend") {
        withJava()
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = listOf("-Xjsr305=strict")
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

    nativeTarget.apply {
       binaries {
           executable {
               entryPoint = "ru.syntez.main"
           }
       }
   }

    sourceSets {
        val backendMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(kotlin("reflect"))
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
                implementation("org.springframework.boot:spring-boot-starter-web:2.1.7.RELEASE")
            }
        }
        val backendTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }

        val nativeMain by getting
        val nativeTest by getting
    }
}

