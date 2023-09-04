import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.*

plugins {
    id("buzzzzing.plugin.application")
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
    implementation(project(":data"))
    implementation(project(":design-system"))
    implementation(project(":di"))
    implementation(project(":domain"))
    implementation(project(":feature:feature-home"))
    implementation(project(":feature:feature-login-signup"))
    implementation(project(":feature:feature-myinfo"))
    implementation(project(":feature:feature-recommend-place"))
    implementation(project(":feature:feature-bookmark"))
    implementation(project(":remote"))
    implementation(project(":mvi"))
    implementation(project(":core:core-ui"))

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
