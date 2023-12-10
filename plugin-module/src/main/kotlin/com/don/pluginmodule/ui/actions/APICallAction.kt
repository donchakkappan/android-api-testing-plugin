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

package com.don.pluginmodule.ui.actions

import com.don.pluginmodule.data.ManualHitInterceptor
import com.don.pluginmodule.domain.MonitorMessageBus
import com.don.pluginmodule.domain.listeners.APIHitSelectionListener
import com.don.pluginmodule.domain.listeners.ClearAPIHitsListener
import com.don.pluginmodule.domain.topics.APISelectionTopics
import com.don.pluginmodule.domain.topics.ClearAllTopic
import com.don.pluginmodule.ui.components.adapters.HeadersAdapter
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import com.intellij.util.messages.MessageBusConnection
import comdon.shared_module.APIHitDetailsModel
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Action to trigger an API manually
 */
class APICallAction : AnAction("", "", AllIcons.Actions.Upload) {

    var apiHitDetails: APIHitDetailsModel? = null
    private val tableModel = HeadersAdapter

    init {
        subscribeToEvents()
    }

    override fun actionPerformed(e: AnActionEvent) {
        apiHitDetails?.let {
            makeApiCall(it.url)
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
     * makes an HTTP Call
     * @param apiUrl API URL
     */
    private fun makeApiCall(apiUrl: String): String? {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ManualHitInterceptor)
            .build()

        val jSON: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()
        val requestBody: RequestBody? = apiHitDetails?.requestContent?.toRequestBody(jSON)

        val headersBuilder = Headers.Builder()
        tableModel.getAllRows().forEach { header ->
            if (header.name.trim().isNotEmpty() && header.name.trim().isNotEmpty()) {
                headersBuilder.add(header.name, header.value)
            }
        }

        val headers = headersBuilder.build()

        println(headers)

        if (apiHitDetails?.method.toString() == "POST") {
            val request: Request = Request.Builder()
                .url(apiUrl)
                .headers(headers)
                .method(apiHitDetails?.method.toString(), requestBody)
                .build()
            client.newCall(request).execute().use { response ->
                return response.body?.string()
            }
        } else {
            val request: Request = Request.Builder()
                .url(apiUrl)
                .headers(headers)
                .build()
            client.newCall(request).execute().use { response ->
                return response.body?.string()
            }
        }

    }

    /**
     * Subscribe to API Events
     */
    private fun subscribeToEvents() {
        val connection: MessageBusConnection = MonitorMessageBus.getMessageBus().connect()
        connection.subscribe(APISelectionTopics.API_SELECTION_TOPIC, object : APIHitSelectionListener {
            override fun onAPIHitSelection(apiHitDetails: APIHitDetailsModel) {
                this@APICallAction.apiHitDetails = apiHitDetails
            }
        })
        connection.subscribe(ClearAllTopic.CLEAR_API_HITS, object : ClearAPIHitsListener {
            override fun onClearAPIHitsCommand() {
                this@APICallAction.apiHitDetails = null
            }
        })
    }

}