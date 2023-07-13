/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    `kotlin-dsl`
}

group = "com.onewx2m.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("JavaLibrary") {
            id = "buzzzzing.plugin.java.library"
            implementationClass = "JavaLibraryConventionPlugin"
        }
        register("AndroidHiltPlugin") {
            id = "buzzzzing.plugin.hilt"
            implementationClass = "AndroidHiltPlugin"
        }
        register("DomainPlugin") {
            id = "buzzzzing.plugin.domain"
            implementationClass = "DomainPlugin"
        }
        register("DataPlugin") {
            id = "buzzzzing.plugin.data"
            implementationClass = "DataPlugin"
        }
        register("AndroidLibraryPlugin") {
            id = "buzzzzing.plugin.android-library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("AndroidApplicationPlugin") {
            id = "buzzzzing.plugin.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("FeaturePlugin") {
            id = "buzzzzing.plugin.feature"
            implementationClass = "FeaturePlugin"
        }
        register("DesignSystemPlugin") {
            id = "buzzzzing.plugin.design-system"
            implementationClass = "DesignSystemPlugin"
        }
        register("RemotePlugin") {
            id = "buzzzzing.plugin.remote"
            implementationClass = "RemotePlugin"
        }
        register("LocalPlugin") {
            id = "buzzzzing.plugin.local"
            implementationClass = "LocalPlugin"
        }
        register("DiPlugin") {
            id = "buzzzzing.plugin.di"
            implementationClass = "DiPlugin"
        }
        register("MviPlugin") {
            id = "buzzzzing.plugin.mvi"
            implementationClass = "MviPlugin"
        }
        register("CoreUiPlugin") {
            id = "buzzzzing.plugin.core-ui"
            implementationClass = "CoreUiPlugin"
        }
    }
}