
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://devrepo.kakao.com/nexus/content/groups/public/")
    }
}

rootProject.name = "Buzzzzing"
include(":app")
include(":data")
include(":domain")
include(":design-system")
include(":feature")
include(":feature:feature-home")
include(":feature:feature-login-signup")
include(":di")
include(":local")
include(":remote")
include(":mvi")
include(":core")
include(":core:core-ui")
