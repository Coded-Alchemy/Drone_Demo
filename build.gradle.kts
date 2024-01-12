// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugin.ANDROID_APP) version Plugin.Version.ANDROID_APP apply false
    id(Plugin.ANDROID_LIBRARY) version Plugin.Version.ANDROID_LIBRARY apply false
    id(Plugin.KOTLIN_ANDROID) version Plugin.Version.KOTLIN_ANDROID apply false
    id(Plugin.SECRETS) version Plugin.Version.SECRETS apply false
}

buildscript {
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}
