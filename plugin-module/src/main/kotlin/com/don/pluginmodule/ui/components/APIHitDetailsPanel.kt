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

import com.don.pluginmodule.ui.components.tabs.RequestBodyTab
import com.don.pluginmodule.ui.components.tabs.RequestHeadersTab
import com.don.pluginmodule.ui.components.tabs.ResponseBodyTab
import com.don.pluginmodule.ui.components.tabs.ResponseHeadersTab
import com.intellij.ui.components.JBTabbedPane
import javax.swing.SwingConstants

/**
 * UI to display API Hit details
 */
class APIHitDetailsPanel {

    private var tabbedPane: JBTabbedPane = JBTabbedPane(SwingConstants.TOP)

    init {
        tabbedPane.insertTab("Request Headers", null, RequestHeadersTab().get(), "", 0)
        tabbedPane.insertTab("Request Content", null, RequestBodyTab().get(), "", 1)
        tabbedPane.insertTab("Response Headers", null, ResponseHeadersTab().get(), "", 2)
        tabbedPane.insertTab("Response Content", null, ResponseBodyTab().get(), "", 3)
    }

    fun get() = tabbedPane
}