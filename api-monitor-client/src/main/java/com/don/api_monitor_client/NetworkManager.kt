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

package com.don.api_monitor_client

import comdon.shared_module.APIHitDetailsModel
import java.io.ObjectOutputStream
import java.io.OutputStream
import java.net.Socket

/**
 * Socket Manager instance to provide socket operations
 */
object NetworkManager {

    lateinit var client: Socket
    private lateinit var objectOutputStream: ObjectOutputStream
    private lateinit var outputStream: OutputStream
    var isServerAlive = false
    var ipAddress: String = ""

    /**
     * Connect to socket server running from Intellij plugin
     */
    fun connectServer(ipAddress: String) {
        this.ipAddress = ipAddress
        client = Socket(ipAddress, 6001)
        objectOutputStream = ObjectOutputStream(client.getOutputStream())
        outputStream = client.getOutputStream()
        println("********** Client Connected ************")
        isServerAlive = true
    }

    /**
     * Send data to server socket
     */
    fun sendData(apiHitDetails: APIHitDetailsModel) {
        if (!this::objectOutputStream.isInitialized) {
            connectServer(this.ipAddress)
            objectOutputStream.writeObject(apiHitDetails)
        } else {
            objectOutputStream.writeObject(apiHitDetails)
        }
    }

}