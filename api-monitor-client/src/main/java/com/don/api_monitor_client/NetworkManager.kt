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