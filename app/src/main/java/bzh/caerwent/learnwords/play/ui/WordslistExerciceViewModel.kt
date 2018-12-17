package bzh.caerwent.learnwords.play.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bzh.caerwent.learnwords.common.ui.PlaySoundViewModel
import bzh.caerwent.learnwords.play.data.model.WordsListSession

open class WordslistExerciceViewModel(session: WordsListSession, hasResponseEditor: Boolean = false) : PlaySoundViewModel(session.words.get(0).item, session.words.get(0).group) {
    protected var mSession: WordsListSession
    protected var mCurrentIndex = MutableLiveData<Int>()


    init {
        mSession = session
        mCurrentIndex.value = 0
    }


    fun getSession(): WordsListSession {
        return mSession
    }

    fun getCurrentIndex(): LiveData<Int> {
        return mCurrentIndex
    }


    fun incrCurrentIndex() {
        mCurrentIndex.value = mCurrentIndex.value!! + 1
        mIdent.value = mSession.words.get(mCurrentIndex.value!!).item
        mGroupIdent.value = mSession.words.get(mCurrentIndex.value!!).group

        mCurrentIndex.postValue(mCurrentIndex.value)
    }


}
