/*
 * Copyright [2023] [Don Chakkappan donchakkappan@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = Sdk.COMPILE_SDK

    defaultConfig {
        applicationId = "com.don.test_app"
        minSdk = Sdk.MIN_SDK
        targetSdk = Sdk.TARGET_SDK
        versionCode = Sdk.VERSION_CODE
        versionName = Sdk.VERSION_NAME

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