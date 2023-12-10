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

package comdon.shared_module

/**
 * Details of an API Hit
 */
data class APIHitDetailsModel(
    val url: String,
    val responseCode: String,
    val responseContentType: String,
    val responseTime: Long,
    val method: String = "GET",
    val requestHeaders: String,
    val requestContent: String,
    val responseHeaders: String,
    val responseContent: String,
    val curlRequest: String
) : java.io.Serializable