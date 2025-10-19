package net.ace.grmw

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.EDT
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.*
import com.intellij.openapi.vfs.newvfs.events.VFileContentChangeEvent
import com.intellij.openapi.vfs.newvfs.events.VFileCreateEvent
import kotlinx.coroutines.*
import java.io.File

class GrmwPostActivity : ProjectActivity {
    override suspend fun execute(project: Project) {
        if (!GrmwConfig.getInstance().state.autoReplaceOnOpen) return
        val dir = File(project.basePath ?: return, "gradle/wrapper")
        val file = File(dir, "gradle-wrapper.properties")
        if (file.exists()) {
            GrmwReplaceLogic.execute(project)
            return
        }
        val done = Key.create<Boolean>("grmw.replaced")
        if (project.getUserData(done) == true) return
        val disposable = Disposable {}
        project.messageBus.connect(disposable).subscribe(ProjectManager.TOPIC, object : ProjectManagerListener {
            override fun projectClosing(p: Project) { if (p === project) Disposer.dispose(disposable) }
        })
        dir.mkdirs()
        LocalFileSystem.getInstance().refreshAndFindFileByIoFile(dir)
        withContext(Dispatchers.IO) {
            for (i in 0..40) {
                if (file.exists()) {
                    withContext(Dispatchers.EDT) {
                        GrmwReplaceLogic.execute(project)
                        project.putUserData(done, true)
                    }
                    return@withContext
                }
                delay(250)
            }
        }
        VirtualFileManager.getInstance().addAsyncFileListener(
            { events ->
                if (events.any {
                        it is VFileCreateEvent || it is VFileContentChangeEvent
                    } && file.exists()
                ) {
                    GrmwReplaceLogic.execute(project)
                    project.putUserData(done, true)
                }
                null
            }, disposable
        )
    }
}