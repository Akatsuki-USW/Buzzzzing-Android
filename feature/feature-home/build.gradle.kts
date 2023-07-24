plugins {
    id("buzzzzing.plugin.feature")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.onewx2m.feature_home"
}

dependencies {
    implementation(libs.lottie)
}