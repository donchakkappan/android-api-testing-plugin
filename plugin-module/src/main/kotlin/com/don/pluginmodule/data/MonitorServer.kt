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

package com.don.pluginmodule.data

import com.don.pluginmodule.domain.MonitorMessageBus
import com.don.pluginmodule.domain.topics.APIHitTopics
import com.don.pluginmodule.utils.showNotification
import comdon.shared_module.APIHitDetailsModel
import java.io.ObjectInputStream
import java.net.ServerSocket

/**
 * Responsible to monitor API hits happening in Android Client App
 */
class MonitorServer {

    var server: ServerSocket? = null

    var listener: (() -> Unit)? = null

    /**
     * Server listening for API details from Client Android App
     */
    fun startServer() {
        try {
            startListening()
        } catch (exception: Exception) {
            startListening()
        }
    }

    fun stopServer() {
        server?.let {
            if (!it.isClosed) {
                it.close()
                showNotification("Stopped Server")
            }
        }
    }

    private fun startListening() {
        if (server != null && !server?.isClosed!!)
            return
        server = ServerSocket(6001)
        val client = server?.accept()

        listener?.invoke()
        println("********** Server Accepted ************")

        val inputStream = client?.getInputStream()
        val objectInputStream = ObjectInputStream(inputStream)

        while (true) {
            while (inputStream?.available() == 0) {
                Thread.sleep(100)
            }

            val networkResponse = objectInputStream.readObject() as APIHitDetailsModel
            publishAppHit(networkResponse)
        }
    }

    /**
     * Publish [APIHitDetailsModel] received from the Android Client to message bus.
     * @param apiHitDetails Details of the API
     */
    private fun publishAppHit(apiHitDetails: APIHitDetailsModel) {
        val messageBus = MonitorMessageBus.getMessageBus()
        messageBus.syncPublisher(APIHitTopics.APP_HIT_TOPIC).onAPICallComplete(apiHitDetails)
    }

}