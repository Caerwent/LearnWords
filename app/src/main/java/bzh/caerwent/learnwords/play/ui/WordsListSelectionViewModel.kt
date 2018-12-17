package bzh.caerwent.learnwords.play.ui

import androidx.lifecycle.ViewModel

class WordsListSelectionViewModel : ViewModel() {

    private var mAdapter=SimpleStringSelectionAdapter()

    public var mUseInput = false


    fun getAdapter():SimpleStringSelectionAdapter
    {
        return mAdapter
    }
}
