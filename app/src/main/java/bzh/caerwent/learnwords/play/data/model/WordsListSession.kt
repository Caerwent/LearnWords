package bzh.caerwent.learnwords.play.data.model

import java.io.Serializable

data class WordsListSessionItem(val group: String = "?", val item: String = "?") : Serializable
data class WordsListSession(val words: MutableList<WordsListSessionItem> = MutableList<WordsListSessionItem>(0, { WordsListSessionItem() })) : Serializable