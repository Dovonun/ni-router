plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ni.router"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ni.router"
        minSdk = 29
        targetSdk = 36
        versionCode = 6
        versionName = "0.0.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            val storeFilePath = System.getenv("RELEASE_STORE_FILE")
            if (storeFilePath != null) {
                storeFile = file(storeFilePath)
                storePassword = System.getenv("RELEASE_STORE_PASSWORD")
                keyAlias = System.getenv("RELEASE_KEY_ALIAS")
                keyPassword = System.getenv("RELEASE_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
            applicationVariants.all {
                outputs.all {
                    val output = this as com.android.build.gradle.internal.api.ApkVariantOutputImpl
                    output.outputFileName = "ni-router-release.apk"
                }
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    testImplementation("junit:junit:4.13.2")
}
