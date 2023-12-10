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

package com.don.pluginmodule.utils

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import okhttp3.Request

/**
 * extension to create Curl string from HTTP request
 */
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

const val NOTIFICATION_GROUP_ID = "APIMonitor"
const val NOTIFICATION_TITLE = "APIMonitor"

fun showNotification(message: String) {
    val notification = Notification(
        NOTIFICATION_GROUP_ID,
        NOTIFICATION_TITLE,
        message,
        NotificationType.INFORMATION
    )
    Notifications.Bus.notify(notification)
}
