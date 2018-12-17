package bzh.caerwent.learnwords.play.ui

import androidx.lifecycle.ViewModel

open class WordslistScoreViewModel(score: Int = 0): ViewModel()  {
    protected var mScore: Int

    init {
        mScore = score
    }

    fun getScore(): Int {
        return mScore
    }


}
