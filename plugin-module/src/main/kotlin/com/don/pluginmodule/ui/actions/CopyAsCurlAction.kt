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

package com.don.pluginmodule.ui.actions

import com.don.pluginmodule.domain.MonitorMessageBus
import com.don.pluginmodule.domain.listeners.APIHitSelectionListener
import com.don.pluginmodule.domain.listeners.ClearAPIHitsListener
import com.don.pluginmodule.domain.topics.APISelectionTopics
import com.don.pluginmodule.domain.topics.ClearAllTopic
import com.intellij.icons.AllIcons
import com.intellij.ide.ClipboardSynchronizer
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.Messages
import com.intellij.util.messages.MessageBusConnection
import comdon.shared_module.APIHitDetailsModel
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.ClipboardOwner
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.Transferable

/**
 * Action to copy API details as a Curl request
 */
class CopyAsCurlAction : AnAction("", "", AllIcons.Actions.Copy), ClipboardOwner {

    var apiHitDetails: APIHitDetailsModel? = null

    init {
        subscribeToEvents()
    }

    override fun actionPerformed(e: AnActionEvent) {
        apiHitDetails?.let {
            ApplicationManager.getApplication().invokeLater {
                val selectedText = it.curlRequest
                if (selectedText.isNotEmpty()) {
                    val transferableText: Transferable =
                        StringSelection(selectedText)
                    val clipboard = ClipboardSynchronizer.getInstance()
                    clipboard.setContent(transferableText, this)

                    Messages.showMessageDialog(
                        e.project,
                        "Curl request copied to clipboard",
                        "Done",
                        Messages.getInformationIcon()
                    )
                }
            }
        } ?: run {
            Messages.showMessageDialog(
                e.project,
                "Please select API details",
                "No API Details",
                Messages.getInformationIcon()
            )
        }
    }

    /**
     * Subscribe to API events
     */
    private fun subscribeToEvents() {
        val connection: MessageBusConnection = MonitorMessageBus.getMessageBus().connect()
        connection.subscribe(APISelectionTopics.API_SELECTION_TOPIC, object : APIHitSelectionListener {
            override fun onAPIHitSelection(apiHitDetails: APIHitDetailsModel) {
                this@CopyAsCurlAction.apiHitDetails = apiHitDetails
            }
        })
        connection.subscribe(ClearAllTopic.CLEAR_API_HITS, object : ClearAPIHitsListener {
            override fun onClearAPIHitsCommand() {
                this@CopyAsCurlAction.apiHitDetails = null
            }
        })
    }

    override fun lostOwnership(clipboard: Clipboard?, contents: Transferable?) {

    }

}