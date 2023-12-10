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

package com.don.pluginmodule.ui

import com.don.pluginmodule.ui.components.APIHitDetailsPanel
import com.don.pluginmodule.ui.components.APIHitListPanel
import com.don.pluginmodule.ui.components.ToolbarOptions
import com.intellij.openapi.ui.SimpleToolWindowPanel
import javax.swing.JSplitPane

/**
 * API Monitor Home UI
 */
class APIMonitorHome(
    vertical: Boolean,
    borderless: Boolean
) : SimpleToolWindowPanel(vertical, borderless) {

    init {
        toolbar = ToolbarOptions()
        val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, APIHitListPanel().get(), APIHitDetailsPanel().get())
        splitPane.dividerSize = 2
        setContent(splitPane)
    }
}