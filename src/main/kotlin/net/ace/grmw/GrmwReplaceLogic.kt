package net.ace.grmw

import com.intellij.openapi.project.Project
import net.ace.grmw.util.GrmwNotifier
import net.ace.grmw.util.GrmwUrlMatcher
import java.io.File
import java.util.*

object GrmwReplaceLogic {
    fun execute(project: Project) {
        val basePath = project.basePath ?: return
        val wrapperFile = File(basePath, "gradle/wrapper/gradle-wrapper.properties")
        if (!wrapperFile.exists()) return

        val props = Properties()
        wrapperFile.inputStream().use { props.load(it) }

        val oldUrl = props.getProperty("distributionUrl") ?: return
        // 只替换官方地址
        if (!oldUrl.contains("services.gradle.org")) return

        val version = GrmwUrlMatcher.extractVersion(oldUrl) ?: return
        val newUrl = GrmwConfig.getInstance().resolveUrl(version)
        if (oldUrl == newUrl) return

        // 写回文件
        props.setProperty("distributionUrl", newUrl)
        wrapperFile.outputStream().use { props.store(it, "Modified by Gradle Wrapper Mirror") }

        // 通知
        GrmwNotifier.info(project, GrmwBundle.message("notify.replaced", newUrl))
    }
}