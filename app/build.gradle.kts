import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.*

plugins {
    id("buzzzzing.plugin.application")
    id("buzzzzing.plugin.application.compose")
    id("com.google.gms.google-services")
    id("buzzzzing.plugin.hilt")
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    defaultConfig {
        applicationId = "com.onewx2m.buzzzzing"
        targetSdk = 33
        versionCode = 3
        versionName = "1.0"

        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            gradleLocalProperties(
                rootDir,
            ).getProperty("KAKAO_NATIVE_APP_KEY"),
        )

        val properties = Properties().apply {
            load(FileInputStream(rootProject.file("local.properties")))
        }
        manifestPlaceholders["KAKAO_MANIFEST_APP_KEY"] =
            properties["KAKAO_MANIFEST_APP_KEY"] as String
    }

    namespace = "com.onewx2m.buzzzzing"

    viewBinding.enable = true
    dataBinding.enable = true
}

dependencies {
    implementation(projects.data)
    implementation(projects.designSystem)
    implementation(projects.di)
    implementation(projects.domain)
    implementation(projects.feature.featureHome)
    implementation(projects.feature.featureLoginSignup)
    implementation(projects.feature.featureMyinfo)
    implementation(projects.feature.featureRecommendPlace)
    implementation(projects.feature.featureBookmark)
    implementation(projects.remote)
    implementation(projects.mvi)
    implementation(projects.core.coreUi)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.splashscreen)
    implementation(libs.bundles.androidx.navigation)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    implementation(libs.bundles.coroutine)
    implementation(libs.kotlinx.serialization.json)

    val bom = libs.firebase.bom
    add("implementation", platform(bom))
    implementation(libs.bundles.firebase)

    implementation(libs.timber)
    implementation(libs.kakao.login)
}
