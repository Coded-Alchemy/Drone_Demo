plugins {
    id(Plugin.ANDROID_APP)
    id(Plugin.KOTLIN_ANDROID)
    id(Plugin.SECRETS)
}

android {
    namespace = Config.NAMESPACE
    compileSdk = Config.COMPILE_SDK

    defaultConfig {
        applicationId = Config.NAMESPACE
        minSdk = Config.MIN_SDK
        targetSdk = Config.TARGET_SDK
        versionCode = Config.VERSION_CODE
        versionName = Config.VERSION_NAME
        multiDexEnabled = true

        testInstrumentationRunner = Config.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(Config.PROGUARD_FILE),
                Config.PROGUARD_RULES
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Config.JVM_TARGET
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Config.KOTLIN_COMPILER_EXTENSION_VERSION
    }
    packaging {
        resources {
            excludes += Config.EXCLUDES
        }
    }
}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":network"))
    implementation(project(":vosk"))

    implementation("com.alphacephei:vosk-android:0.3.47@aar")


    implementation(Dependency.CORE_KTX)
    implementation(Dependency.LIFECYCLE_RUNTIME_KTX)
    implementation(Dependency.COMPOSE_ACTIVITY)
    implementation(platform(Dependency.COMPOSE_BOM))
    implementation(Dependency.COMPOSE_UI)
    implementation(Dependency.COMPOSE_GRAPHICS)
    implementation(Dependency.COMPOSE_UI_PREVIEW)
    implementation(Dependency.COMPOSE_MATERIAL)
    implementation(Dependency.MAV_SDK)
    implementation(Dependency.MAV_SERVER)
    implementation("org.slf4j:slf4j-api:2.0.0")
    implementation("io.insert-koin:koin-android:3.5.0")
    implementation("io.insert-koin:koin-androidx-compose:3.5.0")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.6")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
    implementation("com.google.maps.android:maps-compose:4.1.1")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("net.java.dev.jna:jna:5.13.0@aar")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
