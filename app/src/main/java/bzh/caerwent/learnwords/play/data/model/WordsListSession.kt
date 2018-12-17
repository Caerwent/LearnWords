package bzh.caerwent.learnwords.play.data.model

import java.io.Serializable

data class WordsListSessionItem(val group: String = "?", val item: String = "?", var response: String = "") : Serializable
data class WordsListSession(var useInput: Boolean = false, val words: MutableList<WordsListSessionItem> = MutableList<WordsListSessionItem>(0, { WordsListSessionItem() })) : Serializable