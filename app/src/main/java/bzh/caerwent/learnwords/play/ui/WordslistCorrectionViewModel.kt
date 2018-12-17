package bzh.caerwent.learnwords.play.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import bzh.caerwent.learnwords.play.data.model.WordsListSession

open class WordslistCorrectionViewModel(session: WordsListSession, hasResponseEditor: Boolean = false) : WordslistExerciceViewModel(session, hasResponseEditor) {

    private var mResponse : LiveData<String>

    init {
        mResponse = Transformations.map(mCurrentIndex, {idx -> getSession().words.get(idx).response})
    }
    private var mScore = 0;

    fun getScore(): Int {
        return mScore
    }

    fun incrScore() {
        mScore++
    }



}
