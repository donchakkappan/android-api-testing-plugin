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

package com.don.pluginmodule.ui.components.tabs

import com.don.pluginmodule.domain.MonitorMessageBus
import com.don.pluginmodule.domain.listeners.APICallListener
import com.don.pluginmodule.domain.listeners.APIHitSelectionListener
import com.don.pluginmodule.domain.topics.APIHitTopics
import com.don.pluginmodule.domain.topics.APISelectionTopics
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.messages.MessageBusConnection
import comdon.shared_module.APIHitDetailsModel
import javax.swing.JTextArea

/**
 * UI to display API Response Headers part
 */
class ResponseHeadersTab {

    private var scrollPane: JBScrollPane
    private var contentTextArea: JTextArea = JTextArea()

    init {
        contentTextArea.isEditable = false
        scrollPane = JBScrollPane(contentTextArea)
        subscribeToEvents()
    }

    fun get() = scrollPane

    private fun subscribeToEvents() {
        val connection: MessageBusConnection = MonitorMessageBus.getMessageBus().connect()
        connection.subscribe(APIHitTopics.APP_HIT_TOPIC, object : APICallListener {
            override fun onAPICallComplete(apiHitDetails: APIHitDetailsModel) {
                onEventReceived(apiHitDetails)
            }
        })
        connection.subscribe(APIHitTopics.MANUAL_HIT_TOPIC, object : APICallListener {
            override fun onAPICallComplete(apiHitDetails: APIHitDetailsModel) {
                onEventReceived(apiHitDetails)
            }
        })
        connection.subscribe(APISelectionTopics.API_SELECTION_TOPIC, object : APIHitSelectionListener {
            override fun onAPIHitSelection(apiHitDetails: APIHitDetailsModel) {
                onEventReceived(apiHitDetails)
            }
        })
    }

    private fun onEventReceived(apiHitDetails: APIHitDetailsModel) {
        contentTextArea.text = apiHitDetails.responseHeaders
        contentTextArea.caretPosition = 0
    }
}