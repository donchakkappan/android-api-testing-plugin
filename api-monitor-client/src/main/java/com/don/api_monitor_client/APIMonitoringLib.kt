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
