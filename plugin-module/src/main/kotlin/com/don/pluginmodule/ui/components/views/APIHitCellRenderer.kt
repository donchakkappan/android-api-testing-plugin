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
class APIHitCellRenderer : DefaultTableCellRenderer() {

    override fun getTableCellRendererComponent(
        table: JTable?,
        value: Any,
        isSelected: Boolean,
        hasFocus: Boolean,
        row: Int,
        column: Int
    ): Component {
        val rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
        //rendererComponent.setBackground(table?.getBackground())

        if (isSelected) {
            rendererComponent.background = Color.BLUE
            rendererComponent.foreground = Color.BLUE
        } else {
            // Set the default background color for unselected rows
            rendererComponent.background = UIUtil.getTableBackground()
            rendererComponent.foreground = UIUtil.getTableForeground()
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
    }

}
