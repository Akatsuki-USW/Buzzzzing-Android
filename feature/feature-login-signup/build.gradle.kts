plugins {
    id("buzzzzing.plugin.feature")
}

android {
    namespace = "com.onewx2m.feature_login_signup"

    dataBinding.enable = true
}

dependencies {
    implementation(libs.kakao.login)
    implementation(libs.simpleRatingBar)
    implementation(libs.ted.imagePicker)
    implementation(libs.ted.permission)
    implementation(libs.coil)
}
