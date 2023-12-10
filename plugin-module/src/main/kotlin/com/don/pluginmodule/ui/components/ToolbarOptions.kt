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

package com.don.pluginmodule.ui.components

import com.don.pluginmodule.ui.actions.*
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import java.awt.GridLayout
import javax.swing.JPanel

/**
 * Toolbar to display clear, copy and manual API Hit options
 */
class ToolbarOptions : JPanel(GridLayout()) {

    init {
        val defaultActionGroup = DefaultActionGroup()
        defaultActionGroup.add(StartServerAction())
        defaultActionGroup.add(StopServerAction())
        defaultActionGroup.addSeparator()
        defaultActionGroup.add(ClearAction())
        defaultActionGroup.add(CopyAsCurlAction())
        defaultActionGroup.add(APICallAction())

        add(
            ActionManager.getInstance()
                .createActionToolbar(API_MONITOR_TOOLBAR, defaultActionGroup, false).component
        )
    }

    companion object {
        const val API_MONITOR_TOOLBAR = "APIMonitorToolbar"
    }

}