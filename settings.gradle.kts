enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    // The versionCatalogs block imports common.versions.toml with the commonLibs name, you will use
    // this name to access every dependency saved on this catalog
    versionCatalogs {
        create("commonlibs") {
            from(files("./catalogs/common.versions.toml"))
        }
    }
}

rootProject.name = "SDK"
include(":shared")
