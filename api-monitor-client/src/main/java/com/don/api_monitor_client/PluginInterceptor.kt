package com.don.api_monitor_client

import android.content.Context
import android.content.Intent
import android.os.Build
import com.don.api_monitor_client.utils.generateCurlCommand
import comdon.shared_module.APIHitDetailsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer

/**
 * Interceptor to monitor App side network requests
 */
class PluginInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()
        val requestURL = request.url
        val requestMethod = request.method
        val requestHeaders = request.headers

        val requestBuffer = Buffer()
        request.body?.writeTo(requestBuffer)
        val requestBody = requestBuffer.readUtf8()

        val response: Response = chain.proceed(request)

        val t2 = System.nanoTime()
        val responseTime = (t2 - t1) / 1e6
        val responseHeaders = response.headers

        val responseContentType: MediaType? = response.body?.contentType()
        val responseBody: String = response.body?.string() ?: ""

        val responseCode: Int = response.code

        println(responseBody)

        val networkResponse = APIHitDetailsModel(
            url = requestURL.toString(),
            responseCode = responseCode.toString(),
            responseContentType = responseContentType?.type.toString(),
            responseTime = responseTime.toLong(),
            method = requestMethod,
            requestHeaders = requestHeaders.toString(),
            requestContent = requestBody,
            responseHeaders = responseHeaders.toString(),
            responseContent = responseBody,
            curlRequest = request.generateCurlCommand()
        )

        CoroutineScope(Dispatchers.IO).launch {
            sendData(networkResponse)
        }

        val wrappedBody: ResponseBody = ResponseBody.create(responseContentType, responseBody)
        return response.newBuilder().body(wrappedBody).build()
    }

    /**
     * Send API details to Intellij plugin
     */
    private fun sendData(apiHitDetails: APIHitDetailsModel) {
        Intent(context, MonitorService::class.java).also {
            it.putExtra("DATA", apiHitDetails)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.action = "SEND"
                println("Starting the service in >=26 Mode from a BroadcastReceiver")
                context.startForegroundService(it)
                return
            }
            println("Starting the service in < 26 Mode from a BroadcastReceiver")
            context.startService(it)
        }
    }
}