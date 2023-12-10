/*
 * Copyright [2023] [Don Chakkappan]
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
import com.don.pluginmodule.utils.generateCurlCommand
import comdon.shared_module.APIHitDetailsModel
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer

/**
 * Interceptor to see the manual API hit traffic
 */
object ManualHitInterceptor : Interceptor {

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

        publish(networkResponse)

        val wrappedBody: ResponseBody = ResponseBody.create(responseContentType, responseBody)
        return response.newBuilder().body(wrappedBody).build()
    }

    /**
     * Publish the Manual API Hit event to the message bus
     *
     * @param apiHitDetails Details of the API
     */
    private fun publish(apiHitDetails: APIHitDetailsModel) {
        val messageBus = MonitorMessageBus.getMessageBus()
        messageBus.syncPublisher(APIHitTopics.MANUAL_HIT_TOPIC).onAPICallComplete(apiHitDetails)
    }

}