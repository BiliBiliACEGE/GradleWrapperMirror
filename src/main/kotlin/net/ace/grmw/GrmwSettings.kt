package net.ace.grmw

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent

class GrmwSettings : Configurable {

    private val config = GrmwConfig.getInstance()

    private val mirrorItems = linkedMapOf(
        GrmwBundle.message("mirror.official") to GrmwConfig.Mirror.OFFICIAL.key,
        GrmwBundle.message("mirror.aliyun")   to GrmwConfig.Mirror.ALIYUN.key,
        GrmwBundle.message("mirror.tencent")  to GrmwConfig.Mirror.TENCENT.key,
        GrmwBundle.message("mirror.tuna")     to GrmwConfig.Mirror.TUNA.key,
        GrmwBundle.message("mirror.custom")   to GrmwConfig.Mirror.CUSTOM.key
    )

    private val combo = ComboBox(mirrorItems.keys.toTypedArray())

    private val customField = JBTextField()
    private val autoReplaceCheck = JBCheckBox(GrmwBundle.message("settings.autoReplace"))

    init {
        combo.addActionListener { updateCustomField() }
        reset()
    }

    override fun createComponent(): JComponent? =
        FormBuilder.createFormBuilder()
            .addLabeledComponent(GrmwBundle.message("settings.mirror"), combo)
            .addLabeledComponent(GrmwBundle.message("settings.custom"), customField)
            .addComponent(autoReplaceCheck)
            .panel

    override fun isModified(): Boolean =
        combo.selectedItem.let { mirrorItems[it] } != config.state.mirrorKey
                || customField.text != config.state.customUrl
                || autoReplaceCheck.isSelected != config.state.autoReplaceOnOpen

    override fun apply() {
        config.state.mirrorKey = mirrorItems[combo.selectedItem] ?: GrmwConfig.Mirror.ALIYUN.key
        config.state.customUrl = customField.text.trim()
        config.state.autoReplaceOnOpen = autoReplaceCheck.isSelected

        ProjectManager.getInstance().openProjects.forEach { prj ->
            GrmwReplaceLogic.execute(prj)
        }
    }

    override fun reset() {
        val currentKey = config.state.mirrorKey
        val entry = mirrorItems.entries.find { it.value == currentKey }
        combo.selectedItem = entry?.key ?: mirrorItems.keys.first()

        customField.text = config.state.customUrl
        autoReplaceCheck.isSelected = config.state.autoReplaceOnOpen
        updateCustomField()
    }

    override fun getDisplayName() = GrmwBundle.message("settings.displayName")

    private fun updateCustomField() {
        customField.isEnabled = mirrorItems[combo.selectedItem] == GrmwConfig.Mirror.CUSTOM.key
    }
}