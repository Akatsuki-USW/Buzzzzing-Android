plugins {
    id("buzzzzing.plugin.application")
}

android {
    namespace = "com.onewx2m.buzzzzing"
}

dependencies {
    implementation(libs.kakao.login)
}
