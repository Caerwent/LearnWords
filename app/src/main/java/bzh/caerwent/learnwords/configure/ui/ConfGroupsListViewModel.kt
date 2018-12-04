package bzh.caerwent.learnwords.configure.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import bzh.caerwent.learnwords.common.data.WordsListProvider

class ConfGroupsListViewModel : ViewModel() {


    private val mWordsList: LiveData<MutableList<String>> = WordsListProvider.INSTANCE.getGroups()

    fun getWordsList(): LiveData<MutableList<String>> {
        return mWordsList
    }


    fun canAddItem(aItemName: String): Boolean {
        return !aItemName.isNullOrBlank() && !(mWordsList.value?.contains(aItemName) ?: true)
    }

    fun addItem(aItemName: String) {
        WordsListProvider.INSTANCE.addGroup(aItemName)
    }

    fun removeItem(aItemName: String) {
        WordsListProvider.INSTANCE.removeGroup(aItemName)
    }

}
