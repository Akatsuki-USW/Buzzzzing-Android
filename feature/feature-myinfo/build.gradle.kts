plugins {
    id("buzzzzing.plugin.feature")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.onewx2m.feature_myinfo"

    dataBinding.enable = true
}

dependencies {
    implementation(libs.ted.imagePicker)
    implementation(libs.ted.permission)
    implementation(libs.coil)
    implementation(libs.lottie)
}
