plugins {
    id("buzzzzing.plugin.navigation")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.onewx2m.navigation"
}
dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}
