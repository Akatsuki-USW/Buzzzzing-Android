plugins {
    id("buzzzzing.plugin.android-library")
}

android {
    namespace = "com.onewx2m.mvi"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)

    implementation(libs.timber)

    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.coroutine)
}