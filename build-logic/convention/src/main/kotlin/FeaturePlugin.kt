import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal class FeaturePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("buzzzzing.plugin.hilt")
                apply("buzzzzing.plugin.android-library")
                apply("androidx.navigation.safeargs.kotlin")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                "implementation"(project(":domain"))
                "implementation"(project(":navigation"))

                "implementation"(libs.findLibrary("androidx.appcompat").get())
                "implementation"(libs.findLibrary("androidx.core.ktx").get())
                "implementation"(libs.findLibrary("androidx.constraintlayout").get())
                "implementation"(libs.findLibrary("navigation.fragment.ktx").get())
                "implementation"(libs.findLibrary("navigation.ui.ktx").get())

                "implementation"(libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
                "implementation"(libs.findLibrary("androidx.lifecycle.viewmodel.ktx").get())
                "implementation"(libs.findLibrary("androidx.activity.ktx").get())
                "implementation"(libs.findLibrary("androidx.fragment.ktx").get())
                "implementation"(libs.findLibrary("androidx.recyclerView").get())
                "implementation"(libs.findLibrary("google.material").get())

                "implementation"(libs.findLibrary("kotlinx.coroutines.android").get())
                "implementation"(libs.findLibrary("kotlinx.coroutines.core").get())
            }
        }
    }
}