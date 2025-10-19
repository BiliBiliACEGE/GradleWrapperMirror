package net.ace.grmw

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class GrmwPostActivity : ProjectActivity {

    override suspend fun execute(project: Project) {
        if (!GrmwConfig.getInstance().state.autoReplaceOnOpen) return
        GrmwReplaceLogic.execute(project)
    }
}