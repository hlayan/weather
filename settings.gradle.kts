pluginManagement {
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
    }
}
rootProject.name = "weather"
include(":app")

include(":core:data")
include(":core:datastore")
include(":core:database")
include(":core:network")
include(":core:ui")
include(":core:model")
include(":core:designsystem")

include(":feature:home")
include(":feature:location")
