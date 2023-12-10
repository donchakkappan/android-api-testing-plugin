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

package com.don.pluginmodule.ui.actions

import com.don.pluginmodule.domain.MonitorMessageBus
import com.don.pluginmodule.domain.topics.ClearAllTopic
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * Action to clear all API Hits Details
 */
class ClearAction : AnAction("", "", AllIcons.Actions.GC) {

    override fun actionPerformed(e: AnActionEvent) {
        publishClearAllEvent()
    }

    /**
     * Publish event to clear all API details
     */
    private fun publishClearAllEvent() {
        val messageBus = MonitorMessageBus.getMessageBus()
        messageBus.syncPublisher(ClearAllTopic.CLEAR_API_HITS).onClearAPIHitsCommand()
    }

}