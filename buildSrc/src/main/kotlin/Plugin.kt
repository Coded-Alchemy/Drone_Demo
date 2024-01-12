/**
 * Object to store build plugins.
 *
 * @author Taji Abdullah
 * */
object Plugin {
    object Version {
        const val ANDROID_APP = "8.1.2"
        const val ANDROID_LIBRARY = ANDROID_APP
        const val KOTLIN_ANDROID = "1.9.10"
        const val SECRETS = "2.0.1"
    }

    const val ANDROID_APP = "com.android.application"
    const val ANDROID_LIBRARY = "com.android.library"
    const val KOTLIN_ANDROID = "org.jetbrains.kotlin.android"
    const val SECRETS = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
}