/**
 * Config.kt
 *
 * Object to store project configuration data.
 * @author Taji Abdullah
 * */
object Config {
    const val nameSpace = "coded.alchemy.dronedemo"
    const val compileSdk = 34
    const val minSdk = 24
    const val targetSdk = compileSdk
    const val versionCode = 1
    const val versionName = "1.0"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val proGuardFile = "proguard-android-optimize.txt"
    const val proGuardRules = "proguard-rules.pro"
    const val proGuardConsumer = "consumer-rules.pro"
    const val jvmTarget = "1.8"
    const val kotlinCompilerExtensionVersion = "1.5.3"
    const val excludes = "/META-INF/{AL2.0,LGPL2.1}"
}
