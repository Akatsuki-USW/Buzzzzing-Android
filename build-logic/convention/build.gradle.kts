plugins {
    `kotlin-dsl`
}

group = "com.onewx2m.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("JavaLibraryPlugin") {
            id = "buzzzzing.plugin.java.library"
            implementationClass = "JavaLibraryConventionPlugin"
        }
        register("AndroidHiltPlugin") {
            id = "buzzzzing.plugin.hilt"
            implementationClass = "AndroidHiltPlugin"
        }
        register("AndroidLibraryPlugin") {
            id = "buzzzzing.plugin.android-library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("AndroidApplicationPlugin") {
            id = "buzzzzing.plugin.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("FeaturePlugin") {
            id = "buzzzzing.plugin.feature"
            implementationClass = "FeaturePlugin"
        }
    }
}
