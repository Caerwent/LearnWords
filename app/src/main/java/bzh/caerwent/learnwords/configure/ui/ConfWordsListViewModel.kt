package bzh.caerwent.learnwords.configure.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bzh.caerwent.learnwords.common.data.WordsListProvider

class ConfWordsListViewModel constructor(ident: String) : ViewModel() {


    private var mIdent = MutableLiveData<String>()

    private var mWordsList: LiveData<MutableList<String>>

    init {
        mIdent.value = ident
        mWordsList = WordsListProvider.INSTANCE.getListForGroup(ident)
    }

    fun getIdent(): LiveData<String> {
        return mIdent
    }


    fun getWordsList(): LiveData<MutableList<String>> {
        return mWordsList
    }

    fun canAddItem(aItemName: String): Boolean {
        return !aItemName.isNullOrBlank() && !(mWordsList.value?.contains(aItemName) ?: true)
    }

    fun removeItem(aItemName: String) {
        WordsListProvider.INSTANCE.removeItemFromGroup(mIdent.value!!, aItemName)
    }


}

