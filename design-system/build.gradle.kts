plugins {
    id("buzzzzing.plugin.android-library")
}

android {
    namespace = "com.onewx2m.design_system"
}

dependencies {
    implementation(project(":core:core-ui"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.recyclerview)

    implementation(libs.google.material)

    implementation(libs.coil)
    implementation(libs.timber)
    implementation(libs.lottie)
}
