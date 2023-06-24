import com.android.build.gradle.LibraryExtension
import com.onewx2m.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal class MviPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("buzzzzing.plugin.android-library")
            }

            extensions.configure<LibraryExtension> {
                dataBinding.enable = true
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {

                "implementation"(libs.findLibrary("androidx.appcompat").get())
                "implementation"(libs.findLibrary("androidx.core.ktx").get())

                "implementation"(libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
                "implementation"(libs.findLibrary("androidx.lifecycle.viewmodel.ktx").get())
                "implementation"(libs.findLibrary("androidx.activity.ktx").get())
                "implementation"(libs.findLibrary("androidx.fragment.ktx").get())

                "implementation"(libs.findLibrary("kotlinx.coroutines.android").get())
                "implementation"(libs.findLibrary("kotlinx.coroutines.core").get())

                "implementation"(libs.findLibrary("timber").get())
            }
        }
    }
}