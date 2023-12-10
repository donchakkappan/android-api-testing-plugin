plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.don.test_app"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":api-monitor-client"))

    implementation(Android.ANDROIDX_CORE)
    implementation(Android.ANDROIDX_APP_COMPAT)
    implementation(Android.ANDROIDX_MATERIAL)
    implementation(IntelliJ.OK_HTTP_LOGGER)
    implementation(Android.CONSTRAINTLAYOUT)
    implementation(Android.GSON)
    implementation(Android.RETROFIT)
    implementation(Android.CONVERTER_GSON)
    implementation(Android.LIFECYCLE_RUNTIME)
    implementation(Android.COROUTINES_ANDROID)

    testImplementation(Testing.JUNIT)
    androidTestImplementation(Testing.ANDROIDX_JUNIT)
    androidTestImplementation(Testing.ESPRESSO)
}

repositories {
    google()
    mavenCentral()
    jcenter()
}