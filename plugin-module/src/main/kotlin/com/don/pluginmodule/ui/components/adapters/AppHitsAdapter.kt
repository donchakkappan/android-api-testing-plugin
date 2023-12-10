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

package com.don.pluginmodule.ui.components.adapters

import com.google.common.collect.Lists
import comdon.shared_module.APIHitDetailsModel
import javax.swing.table.AbstractTableModel

/**
 * Adapter to set API Hit details to the App API Hits Section
 */
class AppHitsAdapter : AbstractTableModel() {

    private val tableDataset: MutableList<APIHitDetailsModel> = Lists.newArrayList()

    companion object {
        const val COLUMN_1 = 0
        private val COLUMNS: List<String> = Lists.newArrayList("App Hits")
    }

    override fun getRowCount(): Int = tableDataset.size

    /**
     * Add a [APIHitDetailsModel] to the table.
     *
     * @param apiHitDetails [APIHitDetailsModel]
     */
    fun addRow(apiHitDetails: APIHitDetailsModel) {
        tableDataset.add(apiHitDetails)
        fireTableRowsInserted(tableDataset.size, tableDataset.size)
    }

    /**
     * Get a [APIHitDetailsModel] from the table.
     *
     * @param rowIndex The row index
     * @return [APIHitDetailsModel]
     */
    fun getRow(rowIndex: Int): APIHitDetailsModel {
        return tableDataset[rowIndex]
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any = when (columnIndex) {
        COLUMN_1 -> {
            val url = getRow(rowIndex).url
            getRow(rowIndex).method +
                    " : " + url.takeLast(25) + " " +
                    getRow(rowIndex).responseCode +
                    " : " + getRow(rowIndex).responseTime.toString() + "ms"
        }

        else -> ""
    }

    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean = true

    override fun getColumnCount(): Int = 1

    override fun getColumnName(column: Int): String = COLUMNS[column]

    /**
     * Clear all entries from the table.
     */
    fun clear() {
        tableDataset.clear()
        fireTableDataChanged()
    }
}