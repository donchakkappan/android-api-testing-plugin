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

import android.content.Context
import android.content.Intent
import android.os.Build
import com.don.api_monitor_client.MonitorService.Companion.DATA_IP
import okhttp3.Interceptor

/**
 * Singleton instance for Client library
 */
object APIMonitoringLib {

    /**
     * Returns a network interceptor for HTTP
     */
    fun monitoringInterceptor(context: Context, ipAddress: String): Interceptor {
        startMonitorService(context,ipAddress)
        return PluginInterceptor(context)
    }

    /**
     * To start monitor service
     */
    private fun startMonitorService(context: Context, ipAddress: String) {
        Intent(context, MonitorService::class.java).also {
            it.putExtra(DATA_IP,ipAddress)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.action = "START"
                println("Starting the service in >=26 Mode from a BroadcastReceiver")
                context.startForegroundService(it)
                return
            }
            println("Starting the service in < 26 Mode from a BroadcastReceiver")
            context.startService(it)
        }
    }


}
