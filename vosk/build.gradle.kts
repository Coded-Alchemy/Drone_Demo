import java.util.UUID

plugins {
    id(Plugin.ANDROID_LIBRARY)
}

android {
    namespace = "${Config.NAMESPACE}.vosk"
    compileSdk = Config.COMPILE_SDK

    defaultConfig {
        minSdk = Config.MIN_SDK

        testInstrumentationRunner = Config.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles(Config.PROGUARD_RULES)
    }

    buildFeatures {
        buildConfig = false
    }
    sourceSets {
        this.getByName("main") {
            this.java.srcDir("$buildDir/generated/assets")
        }
    }
}

tasks.register("genUUID") {
    val uuid = UUID.randomUUID().toString()
    val dir = file("$buildDir/generated/assets/model-en-us")
    val file = file("$dir/uuid")

    doLast {
        dir.mkdirs()
        file.writeText(uuid)
    }
}

tasks.named("preBuild") {
    dependsOn("genUUID")
}
