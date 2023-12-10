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

package com.don.pluginmodule.ui.components.tables

import com.don.pluginmodule.domain.MonitorMessageBus
import com.don.pluginmodule.domain.listeners.APICallListener
import com.don.pluginmodule.domain.listeners.ClearAPIHitsListener
import com.don.pluginmodule.domain.topics.APIHitTopics
import com.don.pluginmodule.domain.topics.APISelectionTopics
import com.don.pluginmodule.domain.topics.ClearAllTopic
import com.don.pluginmodule.ui.components.adapters.ManualHitsAdapter
import com.intellij.openapi.application.ApplicationManager
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.table.JBTable
import com.intellij.util.messages.MessageBusConnection
import comdon.shared_module.APIHitDetailsModel
import java.awt.Component
import java.awt.Dimension
import javax.swing.JLabel
import javax.swing.event.ListSelectionEvent
import javax.swing.table.DefaultTableCellRenderer

/**
 * UI to display Manual API Hits
 */
class ManualHitsTable {

    private var appHitsComponent: Component
    private val tableModel = ManualHitsAdapter()
    private val table = JBTable(tableModel)

    init {
        val rightAlignedTableCellRenderer = DefaultTableCellRenderer()
        rightAlignedTableCellRenderer.horizontalAlignment = JLabel.LEFT
        table.columnModel.getColumn(0).cellRenderer = rightAlignedTableCellRenderer

        table.setSelectionBackground(JBColor.GREEN)

        table.selectionModel.addListSelectionListener { _: ListSelectionEvent? ->
            if (table.selectedRow >= 0) {
                ApplicationManager.getApplication().invokeLater {
                    val requestResponse = tableModel.getRow(table.selectedRow)
                    //table.selectionModel.clearSelection()
                    //table.selectionModel.addSelectionInterval(table.selectedRow, table.selectedRow)
                    publish(requestResponse)
                }
            }
        }
        val pane = JBScrollPane(table)
        val preferredSize = Dimension(300, 200)
        pane.preferredSize = preferredSize
        pane.minimumSize = preferredSize
        appHitsComponent = pane

        subscribeToEvents()
    }

    fun get() = appHitsComponent

    private fun subscribeToEvents() {
        val connection: MessageBusConnection = MonitorMessageBus.getMessageBus().connect()
        connection.subscribe(APIHitTopics.MANUAL_HIT_TOPIC, object : APICallListener {
            override fun onAPICallComplete(apiHitDetails: APIHitDetailsModel) {
                onEventReceived(apiHitDetails)
            }
        })
        connection.subscribe(APIHitTopics.APP_HIT_TOPIC, object : APICallListener {
            override fun onAPICallComplete(apiHitDetails: APIHitDetailsModel) {
                table.selectionModel.clearSelection()
            }
        })
        connection.subscribe(ClearAllTopic.CLEAR_API_HITS, object : ClearAPIHitsListener {
            override fun onClearAPIHitsCommand() {
                tableModel.clear()
            }
        })
    }

    private fun onEventReceived(apiHitDetails: APIHitDetailsModel) {
        ApplicationManager.getApplication().invokeLater {
            tableModel.addRow(apiHitDetails)
            val rowCount = tableModel.rowCount - 1
            table.selectionModel.clearSelection()
            table.selectionModel.addSelectionInterval(rowCount, rowCount)
        }
    }

    private fun publish(apiHitDetails: APIHitDetailsModel) {
        val messageBus = MonitorMessageBus.getMessageBus()
        messageBus.syncPublisher(APISelectionTopics.API_SELECTION_TOPIC).onAPIHitSelection(apiHitDetails)
    }
}