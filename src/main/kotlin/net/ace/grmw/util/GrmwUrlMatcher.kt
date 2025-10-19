package net.ace.grmw.util

object GrmwUrlMatcher {
    private val regex = Regex("""gradle-(\d+\.\d+(?:\.\d+)?)-((bin|all)\.zip)""")

    fun extractVersion(url: String): String? = regex.find(url)?.groupValues?.get(1)
}