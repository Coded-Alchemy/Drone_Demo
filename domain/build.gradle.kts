plugins {
    id(Plugin.ANDROID_LIBRARY)
    id(Plugin.KOTLIN_ANDROID)
}

android {
    namespace = "${Config.NAMESPACE}.domain"
    compileSdk = Config.COMPILE_SDK

    defaultConfig {
        minSdk = Config.MIN_SDK

        testInstrumentationRunner = Config.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles(Config.PROGUARD_CONSUMER)
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
}

dependencies {

    implementation(project(":data"))

    implementation(Dependency.APP_COMPAT)
    implementation(Dependency.CORE_KTX)
    implementation(Dependency.KOIN)
    implementation(Dependency.MAV_SDK)
    implementation(Dependency.MAV_SERVER)
    testImplementation("junit:junit:4.13.2")
}