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

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import android.os.SystemClock
import android.widget.Toast
import com.don.api_monitor_client.utils.createServiceNotification
import comdon.shared_module.APIHitDetailsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Client service to send API Hit details to Intellij plugin
 */
class MonitorService : Service() {

    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false
    val networkManager = NetworkManager

    companion object {
        const val DATA_IP = "DATA_IP"
    }

    override fun onBind(intent: Intent): IBinder? {
        println("Some component want to bind with the service")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("onStartCommand executed with startId: $startId")
        if (intent != null) {
            val action = intent.action
            println("using an intent with action $action")
            when (action) {
                "START" -> {
                    val ipAddress = intent.getStringExtra(DATA_IP)
                    ipAddress?.let { startService(it) }
                }

                "SEND" -> {
                    GlobalScope.launch(Dispatchers.IO) {
                        launch(Dispatchers.IO) {
                            val data = intent.getSerializableExtra("DATA") as APIHitDetailsModel
                            if (networkManager.isServerAlive)
                                networkManager.sendData(data)
                        }
                    }
                }

                "STOP" -> stopService()
                else -> println("This should never happen. No action in the received intent")
            }
        } else {
            println(
                "with a null intent. It has been probably restarted by the system."
            )
        }
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        println("The service has been created".toUpperCase())
        val notification = createServiceNotification()
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        println("The service has been destroyed".toUpperCase())
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show()
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        val restartServiceIntent = Intent(applicationContext, MonitorService::class.java).also {
            it.setPackage(packageName)
        }
        val restartServicePendingIntent: PendingIntent =
            PendingIntent.getService(this, 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT)
        applicationContext.getSystemService(Context.ALARM_SERVICE)
        val alarmService: AlarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmService.set(
            AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + 1000,
            restartServicePendingIntent
        )
    }

    private fun startService(ipAddress: String) {
        if (isServiceStarted) return
        println("Starting the foreground service task")
        Toast.makeText(this, "Service starting its task", Toast.LENGTH_SHORT).show()
        isServiceStarted = true

        // we need this lock so our service gets not affected by Doze Mode
        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MonitorService::lock").apply {
                    acquire()
                }
            }

        GlobalScope.launch(Dispatchers.IO) {
            launch(Dispatchers.IO) {
                networkManager.connectServer(ipAddress)
            }
            println("End of the loop for the service")
        }
    }

    private fun stopService() {
        println("Stopping the foreground service")
        Toast.makeText(this, "Service stopping", Toast.LENGTH_SHORT).show()
        try {
            wakeLock?.let {
                if (it.isHeld) {
                    it.release()
                }
            }
            stopForeground(true)
            stopSelf()
        } catch (e: Exception) {
            println("Service stopped without being started: ${e.message}")
        }
        isServiceStarted = false
    }

}