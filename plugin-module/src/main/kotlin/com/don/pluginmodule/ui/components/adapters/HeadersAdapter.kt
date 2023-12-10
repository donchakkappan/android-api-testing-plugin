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

package com.don.pluginmodule.ui.components.adapters

import com.don.pluginmodule.ui.components.adapters.models.HeaderModel
import com.google.common.collect.Lists
import javax.swing.table.AbstractTableModel

/**
 * Adapter to set API Headers details to the App API Hits Section
 */
object HeadersAdapter : AbstractTableModel() {

    private val tableDataset: MutableList<HeaderModel> = Lists.newArrayList()
    private val tableDataHashSet: HashSet<HeaderModel> = hashSetOf()
    private const val COLUMN_1 = 0
    private val COLUMNS: List<String> = Lists.newArrayList("")

    override fun getRowCount(): Int = tableDataset.size

    /**
     * Add a [HeaderModel] to the table.
     *
     * @param headerModel [HeaderModel]
     */
    fun addRow(headerModel: HeaderModel) {
        if (!tableDataHashSet.contains(headerModel)) {
            tableDataset.add(headerModel)
            tableDataHashSet.add(headerModel)
            fireTableRowsInserted(tableDataset.size, tableDataset.size)
        }
    }

    fun removeRow(position: Int) {
        if (position >= 0 && position < tableDataset.size) {
            val modelToBeRemoved = tableDataset[position]
            tableDataset.removeAt(position)
            tableDataHashSet.remove(modelToBeRemoved)
            fireTableRowsDeleted(position, position)
        }
    }

    /**
     * Get a [HeaderModel] from the table.
     *
     * @param rowIndex The row index
     * @return [HeaderModel]
     */
    fun getRow(rowIndex: Int): HeaderModel {
        return tableDataset[rowIndex]
    }

    fun getAllRows() = tableDataset

    override fun getColumnCount(): Int = 1

    override fun getColumnName(column: Int): String = COLUMNS[column]

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any = when (columnIndex) {
        COLUMN_1 -> {
            getRow(rowIndex)
        }

        else -> ""
    }

    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean = true

    /**
     * Clear all entries from the table.
     */
    fun clear() {
        tableDataset.clear()
        tableDataHashSet.clear()
        fireTableDataChanged()
    }
}