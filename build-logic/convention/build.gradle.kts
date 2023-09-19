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
        register("AndroidApplicationPlugin") {
            id = "buzzzzing.plugin.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("AndroidApplicationComposePlugin") {
            id = "buzzzzing.plugin.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("JavaLibraryPlugin") {
            id = "buzzzzing.plugin.java.library"
            implementationClass = "JavaLibraryConventionPlugin"
        }
        register("AndroidHiltPlugin") {
            id = "buzzzzing.plugin.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("AndroidLibraryPlugin") {
            id = "buzzzzing.plugin.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("AndroidLibraryComposePlugin") {
            id = "buzzzzing.plugin.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("FeaturePlugin") {
            id = "buzzzzing.plugin.feature"
            implementationClass = "FeatureConventionPlugin"
        }
    }
}
