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

package com.don.api_monitor_client.utils

import comdon.shared_module.APIHitDetailsModel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.ObjectInputStream
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class TestServer {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            TestServer().server()
        }
    }

    fun server() {
        val server = ServerSocket(6001)
        val client = server.accept()

        println("********** Server Accepted ************")

        val inputStream = client.getInputStream()
        val objectInputStream = ObjectInputStream(inputStream)

        while (true) {
            while (inputStream.available() == 0) {
                Thread.sleep(100)
            }

            val networkResponse = objectInputStream.readObject() as APIHitDetailsModel
            println(networkResponse)
        }
    }

    fun client() {
        val client = Socket("127.0.0.1", 9999)
        val output = PrintWriter(client.getOutputStream(), true)
        val input = BufferedReader(InputStreamReader(client.inputStream))

        println("Client sending [Hello]")
        output.println("Hello")
        println("Client receiving [${input.readLine()}]")
        client.close()
    }
}