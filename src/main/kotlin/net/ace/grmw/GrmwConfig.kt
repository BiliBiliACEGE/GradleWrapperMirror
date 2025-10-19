package net.ace.grmw

import com.intellij.openapi.components.*

@Service
@State(name = "GrmwConfig", storages = [Storage("GrmwConfig.xml")])
class GrmwConfig : PersistentStateComponent<GrmwConfig.State> {
    data class State(
        var mirrorKey: String = "aliyun",
        var customUrl: String = "",
        var autoReplaceOnOpen: Boolean = true,
        var onlyReplaceOfficial: Boolean = true
    )

    private var myState = State()

    override fun getState(): State = myState
    override fun loadState(state: State) {
        myState = state
    }

    private fun selectedMirror(): Mirror =
        Mirror.entries.find { it.key == myState.mirrorKey } ?: Mirror.ALIYUN

    fun resolveUrl(version: String): String {
        val base = when (selectedMirror()) {
            Mirror.CUSTOM -> myState.customUrl
            else -> selectedMirror().url
        }.trimEnd('/')
        return "$base/gradle-$version-bin.zip"
    }

    enum class Mirror(val key: String, val url: String) {
        OFFICIAL("official", "https://services.gradle.org/distributions"),
        ALIYUN("aliyun", "https://mirrors.aliyun.com/gradle"),
        TENCENT("tencent", "https://mirrors.cloud.tencent.com/gradle"),
        TUNA("tuna", "https://mirrors.tuna.tsinghua.edu.cn/gradle"),
        CUSTOM("custom", "")
    }

    companion object {
        fun getInstance() = service<GrmwConfig>()
    }
}