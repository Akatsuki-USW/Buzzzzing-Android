plugins {
    id("buzzzzing.plugin.feature")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.onewx2m.recommend_place"
    dataBinding.enable = true
}

dependencies {

    implementation(libs.ted.imagePicker)
    implementation(libs.ted.permission)
    implementation(libs.coil)
    implementation(libs.lottie)
}
