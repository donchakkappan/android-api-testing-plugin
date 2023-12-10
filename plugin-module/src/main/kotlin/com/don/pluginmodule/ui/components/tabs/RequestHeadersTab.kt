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

package com.don.pluginmodule.ui.components.tabs

import com.don.pluginmodule.domain.MonitorMessageBus
import com.don.pluginmodule.domain.listeners.*
import com.don.pluginmodule.domain.topics.APIHitTopics
import com.don.pluginmodule.domain.topics.APISelectionTopics
import com.don.pluginmodule.domain.topics.ClearAllTopic
import com.don.pluginmodule.domain.topics.HeaderTopics
import com.don.pluginmodule.ui.components.adapters.HeadersAdapter
import com.don.pluginmodule.ui.components.adapters.models.HeaderModel
import com.don.pluginmodule.ui.components.views.HeaderCellEditor
import com.don.pluginmodule.ui.components.views.HeaderCellRenderer
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.table.JBTable
import com.intellij.util.messages.MessageBusConnection
import comdon.shared_module.APIHitDetailsModel
import java.awt.Dimension
import javax.swing.JLabel

/**
 * UI to display API Request Headers part
 */
class RequestHeadersTab {

    private val tableModel = HeadersAdapter
    private val table = JBTable(tableModel)

    init {

        val rightAlignedTableCellRenderer = HeaderCellRenderer()
        rightAlignedTableCellRenderer.horizontalAlignment = JLabel.LEFT

        table.columnModel.getColumn(0).cellRenderer = rightAlignedTableCellRenderer
        table.columnModel.getColumn(0).cellEditor = HeaderCellEditor()

        val pane = JBScrollPane(table)
        val preferredSize = Dimension(300, 200)

        pane.preferredSize = preferredSize
        pane.maximumSize = preferredSize

        subscribeToEvents()
    }

    fun get() = table

    private fun subscribeToEvents() {
        val connection: MessageBusConnection = MonitorMessageBus.getMessageBus().connect()
        connection.subscribe(APIHitTopics.APP_HIT_TOPIC, object : APICallListener {
            override fun onAPICallComplete(apiHitDetails: APIHitDetailsModel) {
                onEventReceived(apiHitDetails)
            }
        })
        connection.subscribe(APISelectionTopics.API_SELECTION_TOPIC, object : APIHitSelectionListener {
            override fun onAPIHitSelection(apiHitDetails: APIHitDetailsModel) {
                onEventReceived(apiHitDetails)
            }
        })
        connection.subscribe(APIHitTopics.MANUAL_HIT_TOPIC, object : APICallListener {
            override fun onAPICallComplete(apiHitDetails: APIHitDetailsModel) {
                onEventReceived(apiHitDetails)
            }
        })

        connection.subscribe(ClearAllTopic.CLEAR_API_HITS, object : ClearAPIHitsListener {
            override fun onClearAPIHitsCommand() {
                tableModel.clear()
                table.revalidate()
                table.repaint()
            }
        })
        connection.subscribe(HeaderTopics.HEADER_REMOVAL_TOPIC, object : HeaderRemovalListener {
            override fun onHeaderRemoval(position: Int) {
                val row = table.editingRow
                tableModel.removeRow(row)
                table.revalidate()
                table.repaint()
            }
        })
        connection.subscribe(HeaderTopics.HEADER_UPDATE_TOPIC, object : HeaderUpdateListener {
            override fun onHeaderUpdate(header: HeaderModel) {
                val row = table.editingRow
                tableModel.getRow(row).name = header.name
                tableModel.getRow(row).value = header.value
                tableModel.getRow(row).isSelected = header.isSelected

                val lastRow = table.model.rowCount - 1
                val name = tableModel.getRow(lastRow).name
                val value = tableModel.getRow(lastRow).value
                if (name.trim().isNotEmpty() && value.trim().isNotEmpty()) {
                    val dummyHeader = HeaderModel("", "", false)
                    tableModel.addRow(dummyHeader)
                }

                table.revalidate()
                table.repaint()
            }
        })
    }

    private fun onEventReceived(apiHitDetails: APIHitDetailsModel) {
        tableModel.clear()
        table.revalidate()
        table.repaint()
        apiHitDetails.requestHeaders.lines().forEach { line ->
            val index = line.indexOf(':')
            if (index != -1) {
                val name = line.substring(0, index).trim()
                val value = line.substring(index + 1).trim()
                tableModel.addRow(HeaderModel(name, value, true))
            }
        }
        tableModel.addRow(HeaderModel("", "", false))
        table.revalidate()
        table.repaint()
    }
}