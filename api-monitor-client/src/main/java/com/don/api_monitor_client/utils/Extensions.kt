package com.don.api_monitor_client.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import okhttp3.Request

fun Request.generateCurlCommand(): String {
    val curlCommand = StringBuilder("curl")
    curlCommand.append(" '").append(url).append("'")
    curlCommand.append(" -X ").append(method)
    headers.toMultimap().forEach { (name, values) ->
        values.forEach { value ->
            curlCommand.append(" -H '").append(name).append(": ").append(value).append("'")
        }
    }
    if (body != null) {
        curlCommand.append(" --data-binary '").append(body.toString()).append("'")
    }
    return curlCommand.toString()
}

fun ContextWrapper.createServiceNotification(): Notification {
    val notificationChannelId = "ENDLESS SERVICE CHANNEL"

    // depending on the Android API that we're dealing with we will have
    // to use a specific method to create the notification
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            notificationChannelId,
            "Endless Service notifications channel",
            NotificationManager.IMPORTANCE_HIGH
        ).let {
            it.description = "Endless Service channel"
            it.enableLights(true)
            it.lightColor = Color.RED
            it.enableVibration(true)
            it.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            it
        }
        notificationManager.createNotificationChannel(channel)
    }

    val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
        this,
        notificationChannelId
    ) else Notification.Builder(this)

    return builder
        .setContentTitle("Endless Service")
        .setContentText("This is your favorite endless service working")
        .setTicker("Ticker text")
        .setPriority(Notification.PRIORITY_HIGH) // for under android 26 compatibility
        .build()
}