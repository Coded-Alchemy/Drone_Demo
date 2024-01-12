import java.util.UUID

plugins {
    id(Plugin.ANDROID_LIBRARY)
}

android {
    namespace = "coded.alchemy.vosk"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
