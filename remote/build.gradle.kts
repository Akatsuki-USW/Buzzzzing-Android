plugins {
    id("buzzzzing.plugin.android-library")
    id("buzzzzing.plugin.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.onewx2m.remote"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.bundles.coroutine)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)

    implementation(libs.timber)
}
