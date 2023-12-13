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
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

object Plugins {
    private const val VER_GRADLE = "1.6.21"
    private const val VER_ANDROID_TOOLS = "7.0.4"

    const val GRADLE = "org.jetbrains.kotlin:kotlin-gradle-plugin:${VER_GRADLE}"
    const val ANDROID_TOOLS = "com.android.tools.build:gradle:${VER_ANDROID_TOOLS}"
}

dependencies {
    implementation(Plugins.GRADLE)
    implementation(Plugins.ANDROID_TOOLS)
}