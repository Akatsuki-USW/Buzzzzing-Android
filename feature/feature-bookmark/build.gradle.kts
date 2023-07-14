plugins {
    id("buzzzzing.plugin.feature")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.onewx2m.feature_bookmark"
}

dependencies {
    implementation(libs.coil)
}
