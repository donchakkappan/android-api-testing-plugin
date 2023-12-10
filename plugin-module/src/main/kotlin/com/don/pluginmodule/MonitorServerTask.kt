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

import com.don.pluginmodule.data.MonitorServer
import com.don.pluginmodule.domain.MonitorMessageBus
import com.don.pluginmodule.domain.listeners.ServerListener
import com.don.pluginmodule.domain.topics.ServerTopics
import com.don.pluginmodule.utils.showNotification
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.util.messages.MessageBusConnection
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


/**
 * Background Task to run Socket Server
 */
class MonitorServerTask(project: Project?) : Task.Backgroundable(project, "Monitoring Server"),
    KoinComponent {

    private val monitorServer: MonitorServer by inject()

    init {
        subscribeToEvents()
        monitorServer.listener = {
            title = "API Monitoring in progress"
            showNotification("Started Server")
        }
    }

    override fun run(indicator: ProgressIndicator) {
        indicator.fraction = 100.0
        monitorServer.startServer()
        return
    }

    /**
     * Subscribe events
     */
    private fun subscribeToEvents() {
        val connection: MessageBusConnection = MonitorMessageBus.getMessageBus().connect()
        connection.subscribe(ServerTopics.SERVER_TOPIC, object : ServerListener {
            override fun stopSever() {
                monitorServer.stopServer()
            }
        })
    }

}
