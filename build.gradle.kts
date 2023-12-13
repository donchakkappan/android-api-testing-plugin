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
    id("java")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.intellij") version "1.5.2"
}

version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    jcenter() // Warning: this repository is going to shut down soon
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    version.set("2022.1.2")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

buildscript {
    dependencies {
        classpath(Plugins.GRADLE)
        classpath(Plugins.ANDROID_TOOLS)
    }
    repositories {
        google()
        mavenCentral()
    }
}

