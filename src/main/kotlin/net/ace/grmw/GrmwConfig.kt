package net.ace.grmw

import com.intellij.openapi.components.*

@Service
@State(name = "GrmwConfig", storages = [Storage("GrmwConfig.xml")])
class GrmwConfig : PersistentStateComponent<GrmwConfig.State> {
    data class State(
        var mirrorKey: String = "aliyun",
        var customUrl: String = "",
        var autoReplaceOnOpen: Boolean = true,
        var onlyReplaceOfficial: Boolean = true,
        var editableMirrors: MutableList<EditableMirror> = DEFAULT_EDITABLE
    )

    data class EditableMirror(
        var key: String = "",
        var name: String = "",
        var url: String = ""
    )

    private var myState = State()

    override fun getState(): State = myState
    override fun loadState(state: State) {
        myState = state
    }

    fun resolveUrl(version: String): String {
        val base = myState.editableMirrors.find { it.key == myState.mirrorKey }?.url
            ?.takeIf { it.isNotBlank() } ?: myState.customUrl
        return "${base.trimEnd('/')}/gradle-$version-bin.zip"
    }

    companion object {
        fun getInstance() = service<GrmwConfig>()
        val DEFAULT_EDITABLE = mutableListOf(
            EditableMirror("official", "Official", "https://services.gradle.org/distributions"),
            EditableMirror("aliyun", "Aliyun", "https://mirrors.aliyun.com/gradle"),
            EditableMirror("tencent", "Tencent", "https://mirrors.cloud.tencent.com/gradle"),
            EditableMirror("tuna", "Tsinghua Tuna", "https://mirrors.tuna.tsinghua.edu.cn/gradle")
        )
    }
}