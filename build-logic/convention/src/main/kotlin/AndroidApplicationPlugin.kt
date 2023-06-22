import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import com.onewx2m.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal class AndroidApplicationPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("buzzzzing.plugin.hilt")
            }

            extensions.configure<ApplicationExtension> {

                defaultConfig {
                    applicationId = "com.onewx2m.buzzzzing"
                    targetSdk = 33
                    versionCode = 1
                    versionName = "1.0"
                }

                dataBinding.enable = true

                configureKotlinAndroid(this)
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                "implementation"(project(":data"))
                "implementation"(project(":design-system"))
                "implementation"(project(":di"))
                "implementation"(project(":domain"))
                "implementation"(project(":feature:feature-home"))
                "implementation"(project(":feature:feature-login-signup"))
                "implementation"(project(":remote"))

                "implementation"(libs.findLibrary("androidx.appcompat").get())
                "implementation"(libs.findLibrary("androidx.core.ktx").get())
                "implementation"(libs.findLibrary("androidx.constraintlayout").get())
                "implementation"(libs.findLibrary("navigation.fragment.ktx").get())
                "implementation"(libs.findLibrary("navigation.ui.ktx").get())

                "implementation"(libs.findLibrary("retrofit.core").get())
                "implementation"(libs.findLibrary("retrofit.kotlin.serialization").get())
                "implementation"(libs.findLibrary("okhttp.logging").get())
                "implementation"(libs.findLibrary("kotlinx.coroutines.android").get())
                "implementation"(libs.findLibrary("kotlinx.coroutines.core").get())
                "implementation"(libs.findLibrary("kotlinx.serialization.json").get())

                "implementation"(libs.findLibrary("timber").get())
            }
        }
    }
}