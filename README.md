# Weather

Weather is a forecasting Android app built entirely with Kotlin and Jetpack Compose. It follows [Google Recommended App Architecture](https://developer.android.com/topic/architecture) and best practices. It provides weather forecasts for different locations.

## Project Requirements

- Java 17+
- The **latest stable** Android Studio (for easy install use [JetBrains Toolbox](https://www.jetbrains.com/toolbox-app/))

## Screenshots

<table align="center">
   <tr>
      <td><img src="https://github.com/hlayan/weather/blob/main/screenshots/home_expended_light.PNG"></td>
      <td><img src="https://github.com/hlayan/weather/blob/main/screenshots/home_collapsed_dark.PNG"></td>
      <td><img src="https://github.com/hlayan/weather/blob/main/screenshots/search_locations.PNG"></td>
   </tr>
</table>

## Tech Stack

### Core

- 100% [Kotlin](https://kotlinlang.org/)
- 100% [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material3 design](https://m3.material.io/) (UI components)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) (structured concurrency)
- [Kotlin Flow](https://kotlinlang.org/docs/flow.html) (reactive datastream)
- [Hilt](https://dagger.dev/hilt/) (DI)

### Local Persistence
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) (key-value storage)
- [Room DB](https://developer.android.com/training/data-storage/room) (SQLite ORM)

### Networking
- [Retrofit](https://square.github.io/retrofit/) (REST client)
- [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) (JSON serialization)

### Build
- [Gradle KTS](https://docs.gradle.org/current/userguide/kotlin_dsl.html) (Kotlin DSL)
- [Gradle version catalogs](https://developer.android.com/build/migrate-to-catalogs) (dependencies versions)

### Other
- [Sooner](https://dokar3.github.io/compose-sonner/) (Toast)
