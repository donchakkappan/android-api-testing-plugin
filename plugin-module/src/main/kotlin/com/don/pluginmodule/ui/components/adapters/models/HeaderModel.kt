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

package com.don.pluginmodule.ui.components.adapters.models

/**
 * Details of the API Header
 */
data class HeaderModel(var name: String, var value: String, var isSelected: Boolean) {
    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return this === other || (other is HeaderModel && this.name == other.name)
    }
}
