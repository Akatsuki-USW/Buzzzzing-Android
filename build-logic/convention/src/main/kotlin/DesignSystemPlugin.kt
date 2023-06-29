import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal class DesignSystemPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("buzzzzing.plugin.android-library")
                apply("androidx.navigation.safeargs.kotlin")
                apply("buzzzzing.plugin.hilt")
            }

            extensions.configure<LibraryExtension> {
                viewBinding.enable = true
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                "implementation"(project(":core:core-ui"))
                "implementation"(libs.findLibrary("androidx.appcompat").get())
                "implementation"(libs.findLibrary("androidx.core.ktx").get())
                "implementation"(libs.findLibrary("androidx.constraintlayout").get())
                "implementation"(libs.findLibrary("androidx.splashscreen").get())

                "implementation"(libs.findLibrary("androidx.recyclerview").get())
                "implementation"(libs.findLibrary("google.material").get())

                "implementation"(libs.findLibrary("kotlinx.coroutines.android").get())
                "implementation"(libs.findLibrary("kotlinx.coroutines.core").get())

                "implementation"(libs.findLibrary("timber").get())
            }
        }
    }
}