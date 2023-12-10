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

object Plugins {
    private const val VER_GRADLE = "1.6.21"
    private const val VER_ANDROID_TOOLS = "7.0.4"

    const val GRADLE = "org.jetbrains.kotlin:kotlin-gradle-plugin:${VER_GRADLE}"
    const val ANDROID_TOOLS = "com.android.tools.build:gradle:${VER_ANDROID_TOOLS}"
}

object IntelliJ {
    private const val VER_OK_HTTP = "4.9.3"
    private const val VER_OK_HTTP_LOGGER = "4.9.1"

    const val OK_HTTP = "com.squareup.okhttp3:okhttp:${VER_OK_HTTP}"
    const val OK_HTTP_LOGGER = "com.squareup.okhttp3:logging-interceptor:${VER_OK_HTTP_LOGGER}"
}

object Android {

    private const val VER_ANDROIDX_CORE = "1.7.0"
    private const val VER_ANDROIDX_APP_COMPAT = "1.3.1"
    private const val VER_ANDROIDX_MATERIAL = "1.4.0"
    private const val VER_KOTLINX_COROUTINES = "1.3.9"
    private const val VER_CONSTRAINTLAYOUT = "2.1.1"
    private const val VER_GSON = "2.8.9"
    private const val VER_RETROFIT = "2.9.0"
    private const val VER_LIFECYCLE_RUNTIME = "2.4.0"
    private const val VER_COROUTINES_ANDROID = "1.5.2-native-mt"

    const val ANDROIDX_CORE = "androidx.core:core-ktx:${VER_ANDROIDX_CORE}"
    const val ANDROIDX_APP_COMPAT = "androidx.appcompat:appcompat:${VER_ANDROIDX_APP_COMPAT}"
    const val ANDROIDX_MATERIAL = "com.google.android.material:material:${VER_ANDROIDX_MATERIAL}"
    const val KOTLINX_COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines:${VER_KOTLINX_COROUTINES}"
    const val CONSTRAINTLAYOUT = "androidx.constraintlayout:constraintlayout:${VER_CONSTRAINTLAYOUT}"
    const val GSON = "com.google.code.gson:gson:${VER_GSON}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${VER_RETROFIT}"
    const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${VER_RETROFIT}"
    const val LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:${VER_LIFECYCLE_RUNTIME}"
    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${VER_COROUTINES_ANDROID}"

}

object Testing {
    private const val VER_JUNIT = "4.13.2"
    private const val VER_ANDROIDX_JUNIT = "1.1.5"
    private const val VER_ESPRESSO = "3.5.1"

    const val JUNIT = "junit:junit:${VER_JUNIT}"
    const val ANDROIDX_JUNIT = "androidx.test.ext:junit:${VER_ANDROIDX_JUNIT}"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:${VER_ESPRESSO}"
}

object DI {
    private const val VER_KOIN_CORE = "3.2.0"

    const val KOIN_CORE = "io.insert-koin:koin-core:${VER_KOIN_CORE}"
}