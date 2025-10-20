package net.ace.grmw

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBTextField
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.FormBuilder
import java.awt.Component
import javax.swing.AbstractCellEditor
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.table.DefaultTableModel
import javax.swing.table.TableCellEditor
import javax.swing.table.TableCellRenderer

class GrmwSettings : Configurable {
    private val config = GrmwConfig.getInstance()
    private val mirrorItems = linkedMapOf(
        GrmwBundle.message("mirror.official") to "official",
        GrmwBundle.message("mirror.aliyun") to "aliyun",
        GrmwBundle.message("mirror.tencent") to "tencent",
        GrmwBundle.message("mirror.tuna") to "tuna",
        GrmwBundle.message("mirror.custom") to "custom"
    )
    private val combo = ComboBox(mirrorItems.keys.toTypedArray())
    private val customField = JBTextField()
    private val autoReplaceCheck = JBCheckBox(GrmwBundle.message("settings.autoReplace"))
    private val onlyOfficialCheck = JBCheckBox(GrmwBundle.message("settings.onlyReplaceOfficial"))

    private val tableModel = object : DefaultTableModel(arrayOf("Key", "Name", "URL"), 0) {
        override fun isCellEditable(r: Int, c: Int) = c != 1
    }
    private val table = JBTable(tableModel).apply {
        setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION)
        columnModel.getColumn(1).cellRenderer = I18nRenderer()
        columnModel.getColumn(1).cellEditor = I18nEditor()
    }

    private inner class I18nRenderer : TableCellRenderer {
        private val label = com.intellij.ui.components.JBLabel()
        override fun getTableCellRendererComponent(
            table: JTable, value: Any?, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int
        ): Component {
            val key = value as? String ?: ""
            label.text = if (key.isEmpty()) key else GrmwBundle.message("mirror.$key")
            return label
        }
    }

    private inner class I18nEditor : AbstractCellEditor(), TableCellEditor {
        private val comboEd = ComboBox(mirrorItems.keys.toTypedArray())
        override fun getTableCellEditorComponent(
            table: JTable, value: Any?, isSelected: Boolean, row: Int, column: Int
        ): Component {
            val key = value as? String ?: ""
            comboEd.selectedItem = mirrorItems.entries.find { it.value == key }?.key ?: key
            comboEd.addActionListener { stopCellEditing() }
            return comboEd
        }

        override fun getCellEditorValue(): String = mirrorItems[comboEd.selectedItem] ?: ""
    }

    private val tablePanel: JPanel = ToolbarDecorator.createDecorator(table)
        .setAddAction {
            tableModel.addRow(arrayOf("official", "official", "https://"))
        }
        .setRemoveAction {
            val r = table.selectedRow
            if (r >= 0) tableModel.removeRow(r)
        }
        .createPanel()

    init {
        combo.addActionListener { updateCustomField() }
        reset()
    }

    private fun updateCustomField() {
        customField.isEnabled = mirrorItems[combo.selectedItem] == "custom"
    }

    override fun createComponent(): JComponent? =
        FormBuilder.createFormBuilder()
            .addLabeledComponent(GrmwBundle.message("settings.mirror"), combo)
            .addLabeledComponent(GrmwBundle.message("settings.custom"), customField)
            .addComponent(autoReplaceCheck)
            .addComponent(onlyOfficialCheck)
            .addComponentFillVertically(tablePanel, 0)
            .panel

    override fun isModified(): Boolean {
        if (combo.selectedItem.let { mirrorItems[it] } != config.state.mirrorKey) return true
        if (customField.text != config.state.customUrl) return true
        if (autoReplaceCheck.isSelected != config.state.autoReplaceOnOpen) return true
        if (onlyOfficialCheck.isSelected != config.state.onlyReplaceOfficial) return true
        if (tableModel.rowCount != config.state.editableMirrors.size) return true
        for (i in 0 until tableModel.rowCount) {
            val k = tableModel.getValueAt(i, 0) as String
            val u = tableModel.getValueAt(i, 2) as String
            val o = config.state.editableMirrors.getOrNull(i)
            if (o == null || k != o.key || u != o.url) return true
        }
        return false
    }

    override fun apply() {
        config.state.mirrorKey = mirrorItems[combo.selectedItem] ?: "aliyun"
        config.state.customUrl = customField.text.trim()
        config.state.autoReplaceOnOpen = autoReplaceCheck.isSelected
        config.state.onlyReplaceOfficial = onlyOfficialCheck.isSelected
        config.state.editableMirrors.clear()
        for (i in 0 until tableModel.rowCount) {
            config.state.editableMirrors.add(
                GrmwConfig.EditableMirror(
                    tableModel.getValueAt(i, 0) as String,
                    tableModel.getValueAt(i, 0) as String,
                    tableModel.getValueAt(i, 2) as String
                )
            )
        }
        ProjectManager.getInstance().openProjects.forEach { GrmwReplaceLogic.execute(it) }
    }

    override fun reset() {
        val currentKey = config.state.mirrorKey
        val entry = mirrorItems.entries.find { it.value == currentKey }
        combo.selectedItem = entry?.key ?: mirrorItems.keys.first()
        customField.text = config.state.customUrl
        autoReplaceCheck.isSelected = config.state.autoReplaceOnOpen
        onlyOfficialCheck.isSelected = config.state.onlyReplaceOfficial
        tableModel.rowCount = 0
        for (m in config.state.editableMirrors) {
            tableModel.addRow(arrayOf(m.key, m.key, m.url))
        }
        updateCustomField()
    }

    override fun getDisplayName() = GrmwBundle.message("settings.displayName")
}