plugins {
    id("buzzzzing.plugin.core-ui")
    id("buzzzzing.plugin.hilt")
}

android {
    namespace = "com.onewx2m.core_ui"
}

dependencies {
    implementation(libs.ted.permission)
    implementation(libs.coil)
}
