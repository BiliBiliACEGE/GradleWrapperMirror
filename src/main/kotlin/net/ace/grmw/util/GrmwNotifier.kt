package net.ace.grmw.util

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

object GrmwNotifier {
    fun info(project: Project, content: String) =
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Gradle Wrapper Mirror")
            .createNotification(content, NotificationType.INFORMATION)
            .notify(project)
}