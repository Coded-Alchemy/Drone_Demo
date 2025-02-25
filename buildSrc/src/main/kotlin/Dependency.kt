/**
 * Object to store dependencies.
 * */
object Dependency {
    object Version {
        const val APP_COMPAT = "1.6.1"
        const val CORE_KTX = "1.12.0"
        const val LIFECYCLE_RUNTIME_KTX = "2.6.2"
        const val SERIALIZATION = "1.3.0"
        const val COMPOSE_ACTIVITY = "1.7.2"
        const val COMPOSE_BOM = "2023.03.00"
        const val COMPOSE_UI = "ui"
        const val COMPOSE_GRAPHICS = "ui-graphics"
        const val COMPOSE_UI_PREVIEW = "ui-tooling-preview"
        const val COMPOSE_MATERIAL = "material3"
        const val NAVIGATION = "2.7.5"
        const val NAVIGATION_RUNTIME = "2.7.5"
        const val ROOM = "2.5.2"
        const val PAGING = "3.2.1"
        const val PAGING_COMPOSE = "3.3.0-alpha02"
        const val KOIN = "3.5.0"
    }

    const val APP_COMPAT = "androidx.appcompat:appcompat:${Version.APP_COMPAT}"

    // ktx
    const val CORE_KTX = "androidx.core:core-ktx:${Version.CORE_KTX}"
    const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.LIFECYCLE_RUNTIME_KTX}"

    // Compose
    const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose:${Version.COMPOSE_ACTIVITY}"
    const val COMPOSE_BOM = "androidx.compose:compose-bom:${Version.COMPOSE_BOM}"
    const val COMPOSE_UI = "androidx.compose.ui:${Version.COMPOSE_UI}"
    const val COMPOSE_GRAPHICS = "androidx.compose.ui:${Version.COMPOSE_GRAPHICS}"
    const val COMPOSE_UI_PREVIEW = "androidx.compose.ui:${Version.COMPOSE_UI_PREVIEW}"
    const val COMPOSE_MATERIAL = "androidx.compose.material3:${Version.COMPOSE_MATERIAL}"

    // Navigation
    const val NAVIGATION = "androidx.navigation:navigation-compose:${Version.NAVIGATION}"
    const val NAVIGATION_RUNTIME = "androidx.navigation:navigation-runtime-ktx:${Version.NAVIGATION_RUNTIME}"

    // Koin
    const val KOIN = "io.insert-koin:koin-android:${Version.KOIN}"
    const val KOIN_COMPOSE = "io.insert-koin:koin-androidx-compose:${Version.KOIN}"

    // Mav SDK
    const val MAV_SDK = "io.mavsdk:mavsdk:1.3.1"
    const val MAV_SERVER = "io.mavsdk:mavsdk-server:1.3.2"

    // Room
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Version.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Version.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Version.ROOM}"
    const val ROOM_PAGING = "androidx.room:room-paging:${Version.ROOM}"

    // Paging
    const val PAGING_RUNTIME = "androidx.paging:paging-runtime:${Version.PAGING}"
    const val PAGING_COMPOSE = "androidx.paging:paging-compose:${Version.PAGING_COMPOSE}"
}
