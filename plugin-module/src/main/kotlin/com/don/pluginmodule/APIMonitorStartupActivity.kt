/*
 * Copyright [2023] [Don Chakkappan]
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

package com.don.pluginmodule

import com.don.pluginmodule.di.appModule
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import org.koin.core.context.startKoin

/*
 * Start a TCP/IP Server to receive API Hit details from Android Client App
 * Quick schematic
 * ┌────────────┐       ┌──────────────────┐ TCP/IP Socket ┌──────────────┐
 * │Android App ├──────►│ Interceptor (Lib)├──────────────►│ Plugin Module│
 * └────────────┘       └──────────────────┘               └──────────────┘
 */
class APIMonitorStartupActivity : StartupActivity {
    override fun runActivity(project: Project) {
        startKoin {
            modules(appModule)
        }
    }
}
