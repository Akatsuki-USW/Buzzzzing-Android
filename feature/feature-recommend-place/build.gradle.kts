plugins {
    id("buzzzzing.plugin.feature")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.onewx2m.recommend_place"
    dataBinding.enable = true
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.ted.imagePicker)
    implementation(libs.ted.permission)
    implementation(libs.coil)
    implementation(libs.lottie)
    implementation(libs.powerMenu)
}
