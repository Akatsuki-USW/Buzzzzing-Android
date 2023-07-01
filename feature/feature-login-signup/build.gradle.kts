plugins {
    id("buzzzzing.plugin.feature")
}

android {
    namespace = "com.onewx2m.feature_login_signup"
}

dependencies {
    implementation(libs.kakao.login)
    implementation(libs.simple.rating.bar)
}