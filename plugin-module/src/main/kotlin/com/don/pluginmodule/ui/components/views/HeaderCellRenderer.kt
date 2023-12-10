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

import com.don.pluginmodule.ui.components.adapters.models.HeaderModel
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBCheckBox
import com.intellij.util.ui.UIUtil
import java.awt.*
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.JTextField
import javax.swing.border.EmptyBorder
import javax.swing.table.DefaultTableCellRenderer

/**
 * Editor for Header cell renderer
 */
class HeaderCellRenderer : DefaultTableCellRenderer() {
    private val panel: JPanel
    private val checkBox: JBCheckBox
    private val textField1: JTextField
    private val textField2: JTextField
    private val closeIconLabel: JLabel
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

        val textFieldsPanel = JPanel(FlowLayout(FlowLayout.LEFT))
        textFieldsPanel.add(textField1)
        textFieldsPanel.add(textField2)
        textFieldsPanel.add(closeIconLabel)
        textFieldsPanel.add(doneIconLabel)

        panel.add(checkBox, BorderLayout.WEST)
        panel.add(textFieldsPanel, BorderLayout.CENTER)

    }

    override fun getTableCellRendererComponent(
        table: JTable?,
        value: Any,
        isSelected: Boolean,
        hasFocus: Boolean,
        row: Int,
        column: Int
    ): Component {
        val rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
        rendererComponent.setBackground(table?.getBackground())

        if (value is HeaderModel) {
            val dataObject: HeaderModel = value

            if (row != (table?.model?.rowCount?.minus(1) ?: 0)) {
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

}
