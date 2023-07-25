plugins {
    id("buzzzzing.plugin.android-library")
    id("buzzzzing.plugin.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.onewx2m.core_ui"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.recyclerview)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.google.material)

    implementation(libs.timber)
    implementation(libs.ted.permission)
    implementation(libs.coil)

    implementation(libs.bundles.androidx.navigation)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.coroutine)
}
