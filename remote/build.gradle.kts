plugins {
    id("buzzzzing.plugin.android.library")
    id("buzzzzing.plugin.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.onewx2m.remote"

    defaultConfig {
        buildConfigField(
            "String",
            "KAKAO_REST_API_KEY",
            com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(
                rootDir,
            ).getProperty("KAKAO_REST_API_KEY"),
        )
    }
}

dependencies {
    implementation(projects.domain)
    implementation(projects.data)

    implementation(libs.bundles.coroutine)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)

    val bom = libs.firebase.bom
    add("implementation", platform(bom))
    implementation(libs.bundles.firebase)

    implementation(libs.timber)
}
