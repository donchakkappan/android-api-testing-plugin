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

package com.don.pluginmodule.domain.listeners

import comdon.shared_module.APIHitDetailsModel

/**
 * Listener for API Hit
 */
interface APICallListener {
    /**
     * Triggers on completion of API hit
     * @param apiHitDetails Details of the API Hit
     */
    fun onAPICallComplete(apiHitDetails: APIHitDetailsModel)
}