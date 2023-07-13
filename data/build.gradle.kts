plugins {
    id("buzzzzing.plugin.java.library")
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.hilt.core)
}
