plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("maven-publish")
}

var androidTarget: String = ""

kotlin {
    val android = android {
        publishLibraryVariants("release")
    }
    androidTarget = android.name

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "net.globalid.sdk"
    compileSdk = 34
    defaultConfig {
        minSdk = 28
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

publishing {
    repositories {
        maven {
            name = "github"
            url = uri("https://maven.pkg.github.com/andrazhribar/kmp-sdk-showcase")
            credentials {
                username = System.getenv()["MYUSER"]
                password = System.getenv()["MYPAT"]
            }
        }
    }
    val thePublications = listOf(androidTarget) + "kotlinMultiplatform"
    publications {
        matching { it.name in thePublications }.all {
            val targetPublication = this@all
            tasks.withType<AbstractPublishToMaven>()
                .matching { it.publication == targetPublication }
                .configureEach { onlyIf { findProperty("isMainHost") == "true" } }
        }
        matching { it.name.contains("ios", true) }.all {
            val targetPublication = this@all
            tasks.withType<AbstractPublishToMaven>()
                .matching { it.publication == targetPublication }
                .forEach { it.enabled = false }
        }
    }
}
