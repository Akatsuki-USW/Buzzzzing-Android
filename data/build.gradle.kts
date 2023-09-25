plugins {
    id("buzzzzing.plugin.java.library")
}

dependencies {
    implementation(projects.domain)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.hilt.core)
}
