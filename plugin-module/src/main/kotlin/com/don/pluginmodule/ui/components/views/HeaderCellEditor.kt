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

package com.don.pluginmodule.ui.components.views

import com.don.pluginmodule.domain.MonitorMessageBus
import com.don.pluginmodule.domain.topics.HeaderTopics
import com.don.pluginmodule.ui.components.adapters.models.HeaderModel
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBCheckBox
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.table.TableCellEditor

/**
 * Editor for Header Cell
 */
class HeaderCellEditor : AbstractCellEditor(), TableCellEditor {

    private val panel: JPanel
    private val checkBox: JBCheckBox
    private val textField1: JTextField
    private val textField2: JTextField
    private val closeIconLabel: JLabel
    private var isEditing = false
    private val doneIconLabel: JLabel

    init {
        val preferredSize = Dimension(300, 25)

        panel = JPanel(BorderLayout())

        panel.setBorder(EmptyBorder(2, 2, 2, 2))
        checkBox = JBCheckBox()
        checkBox.setOpaque(false)
        textField1 = JTextField()
        textField1.preferredSize = preferredSize
        textField1.minimumSize = preferredSize

        textField1.setBorder(null)
        textField2 = JTextField()
        textField2.preferredSize = preferredSize
        textField2.minimumSize = preferredSize
        textField2.setBorder(null)

        closeIconLabel = JLabel("✖")
        closeIconLabel.setForeground(JBColor.RED)
        closeIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))
        closeIconLabel.setBorder(EmptyBorder(0, 5, 0, 0))

        doneIconLabel = JLabel("✔")
        doneIconLabel.setForeground(JBColor.GREEN)
        doneIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))
        doneIconLabel.setBorder(EmptyBorder(0, 5, 0, 0))

        textField1.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                isEditing = true
                textField1.requestFocusInWindow()
                panel.repaint()
            }
        })
        textField2.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                isEditing = true
                textField2.requestFocusInWindow()
                panel.repaint()
            }
        })

        closeIconLabel.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                publish(0)
                stopCellEditing()
            }
        })

        doneIconLabel.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {

                if (textField1.text.trim().isNotEmpty() && textField2.text.trim().isNotEmpty()) {
                    val header = HeaderModel(textField1.text, textField2.text, true)
                    publish(header)
                }
            }
        })

        val textFieldsPanel = JPanel(FlowLayout(FlowLayout.LEFT))
        textFieldsPanel.add(textField1)
        textFieldsPanel.add(textField2)
        textFieldsPanel.add(closeIconLabel)
        textFieldsPanel.add(doneIconLabel)

        panel.add(checkBox, BorderLayout.WEST)
        panel.add(textFieldsPanel, BorderLayout.CENTER)
    }

    override fun getTableCellEditorComponent(
        table: JTable,
        value: Any,
        isSelected: Boolean,
        row: Int,
        column: Int
    ): Component {

        if (value is HeaderModel) {
            val dataObject: HeaderModel = value

            if (row != (table.model?.rowCount?.minus(1) ?: 0)) {
                checkBox.setSelected(dataObject.isSelected)
                textField1.text = dataObject.name
                textField2.text = dataObject.value
                closeIconLabel.isVisible = true
            } else {
                closeIconLabel.isVisible = false
                textField1.text = ""
                textField2.text = ""
                checkBox.setSelected(false)
            }
        }
        return panel
    }

    override fun getCellEditorValue(): Any {
        return HeaderModel(textField1.getText(), textField2.getText(), false)
    }

    private fun publish(position: Int) {
        val messageBus = MonitorMessageBus.getMessageBus()
        messageBus.syncPublisher(HeaderTopics.HEADER_REMOVAL_TOPIC).onHeaderRemoval(position)
    }

    private fun publish(header: HeaderModel) {
        val messageBus = MonitorMessageBus.getMessageBus()
        messageBus.syncPublisher(HeaderTopics.HEADER_UPDATE_TOPIC).onHeaderUpdate(header)
    }
}