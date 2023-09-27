plugins {
    id("buzzzzing.plugin.android.library")
    id("buzzzzing.plugin.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.onewx2m.di"
}

dependencies {
    implementation(projects.domain)
    implementation(projects.data)
    implementation(projects.remote)
    implementation(projects.local)

    implementation(libs.bundles.coroutine)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)

    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)

    ksp(libs.encrypted.datastore.preference.ksp)
    implementation(libs.encrypted.datastore.preference.ksp.annotations)
    implementation(libs.encrypted.datastore.preference.security)

    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    implementation(libs.timber)
}
