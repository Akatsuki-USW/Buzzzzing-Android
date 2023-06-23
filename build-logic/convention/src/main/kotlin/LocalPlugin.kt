import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSetContainer
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

internal class LocalPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("buzzzzing.plugin.android-library")
                apply("buzzzzing.plugin.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("com.google.devtools.ksp")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                "implementation"(project(":domain"))
                "implementation"(project(":data"))
                "implementation"(libs.findLibrary("kotlinx.coroutines.android").get())
                "implementation"(libs.findLibrary("kotlinx.coroutines.core").get())
                "implementation"(libs.findLibrary("kotlinx.serialization.json").get())

                "implementation"(libs.findLibrary("androidx.dataStore.core").get())
                "implementation"(libs.findLibrary("androidx.dataStore.preferences").get())

                "ksp"(libs.findLibrary("encrypted.datastroe.preference.ksp").get())
                "implementation"(libs.findLibrary("encrypted.datastroe.preference.ksp.annotations").get())
                "implementation"(libs.findLibrary("encrypted.datastroe.preference.security").get())

                "implementation"(libs.findLibrary("timber").get())
            }
        }
    }
}