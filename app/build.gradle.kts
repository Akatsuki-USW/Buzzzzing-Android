plugins {
    id("buzzzzing.plugin.application")
}

android {
    namespace = "com.onewx2m.buzzzzing"

    dataBinding.enable = true
}

dependencies {
    implementation(libs.kakao.login)
}
