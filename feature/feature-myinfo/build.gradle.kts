plugins {
    id("buzzzzing.plugin.feature")
}

android {
    namespace = "com.onewx2m.feature_myinfo"

    dataBinding.enable = true
}

dependencies {
    implementation(libs.ted.imagePicker)
    implementation(libs.ted.permission)
    implementation(libs.coil)
}
