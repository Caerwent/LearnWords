package bzh.caerwent.learnwords.common.data

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FilenameFilter

class WordsListProvider {

    companion object {
        var TAG = MediaManager.javaClass.simpleName
        var STORAGE_LOCATION = Environment.getExternalStorageDirectory().getAbsolutePath() + "/learnwords/"
        var INSTANCE = WordsListProvider()
            private set
    }

    private var mGroupList = MutableLiveData<MutableMap<String, MutableList<String>>>()

    init {
        mGroupList.value = mutableMapOf()
        loadList()
    }

    private object mSoundFileFiltername : FilenameFilter {
        override fun accept(dir: File, filename: String): Boolean {
            return filename.endsWith(".3gp")
        }
    }

    fun loadList() {
        GlobalScope.launch(Dispatchers.Main) {
            loadListFromDisk();
        }
    }

    fun getGroups(): LiveData<MutableList<String>> {
        return Transformations.map(mGroupList, {
            mGroupList.value?.keys?.toMutableList() ?: MutableList(0, { "" })
        })
    }

    fun getListForGroup(aGroupName: String): LiveData<MutableList<String>> {
        return Transformations.map(mGroupList, {
            mGroupList.value?.get(aGroupName) ?: MutableList(0, { "" })
        })
    }
    fun getListForGroupData(aGroupName: String): MutableList<String> {
        return  mGroupList.value?.get(aGroupName) ?: MutableList(0, { "" })
    }

    fun renameGroup(aGroupName: String, aNewGroupName: String) {
        GlobalScope.launch(Dispatchers.Main) {
            if (mGroupList.value?.containsKey(aGroupName) ?: false &&
                    !(mGroupList.value?.containsKey(aNewGroupName) ?: false)) {
                var groupDir = File(STORAGE_LOCATION + aGroupName)
                if (groupDir.isDirectory && groupDir.renameTo(File(STORAGE_LOCATION + aNewGroupName))) {
                    mGroupList.value?.put(aNewGroupName, mGroupList.value?.remove(aGroupName)!!)
                    mGroupList.postValue(mGroupList.value)
                }
            }
        }
    }

    fun addGroup(aGroupName: String) {
        if (!(mGroupList.value?.containsKey(aGroupName) ?: false)) {
            GlobalScope.launch(Dispatchers.Main) {
                var groupDir = File(STORAGE_LOCATION + aGroupName)
                if (groupDir.mkdir()) {
                    mGroupList.value?.put(aGroupName, MutableList(0, { "" }))
                    mGroupList.postValue(mGroupList.value)
                }
            }
        }
    }

    fun removeGroup(aGroupName: String) {
        if (!(mGroupList.value?.containsKey(aGroupName) ?: false)) {
            GlobalScope.launch(Dispatchers.Main) {
                var groupDir = File(STORAGE_LOCATION + aGroupName)
                if (groupDir.deleteRecursively()) {
                    mGroupList.value?.remove(aGroupName)
                    mGroupList.postValue(mGroupList.value)
                }
            }
        }
    }

    fun removeItemFromGroup(aGroupName: String, aItem: String) {
        if ((mGroupList.value?.containsKey(aGroupName) ?: false) &&
                (mGroupList.value?.get(aGroupName)?.contains(aItem) ?: false)) {
            GlobalScope.launch(Dispatchers.Main) {
                var groupDir = File(STORAGE_LOCATION + aGroupName + "/" + aItem + MediaManager.EXT)
                if (groupDir.delete()) {
                    mGroupList.value?.get(aGroupName)?.remove(aItem)
                    mGroupList.postValue(mGroupList.value)
                }
            }
        }
    }

    fun isFileLoaded(aGroupName: String, aItem: String): Boolean {
        return (mGroupList.value?.containsKey(aGroupName) ?: false) &&
                (mGroupList.value?.get(aGroupName)?.contains(aItem) ?: false)
    }

    fun isFileExists(aGroupName: String, aItem: String): Boolean {
        var file = File(STORAGE_LOCATION + aGroupName + "/" + aItem + MediaManager.EXT)
        return file.exists()
    }

    private fun loadListFromDisk() {
        var mainDir = File(STORAGE_LOCATION)
        if (!mainDir.exists()) {
            mainDir.mkdirs();
        }

        if (mainDir.isDirectory) {
            mainDir.listFiles().forEach { currFile ->
                if (currFile.isDirectory) {
                    var currWordList = MutableList(0, { "" })
                    currFile.listFiles(mSoundFileFiltername).forEach { currWordSound ->
                        currWordList.add(currWordSound.nameWithoutExtension)

                    }
                    mGroupList.value?.put(currFile.name, currWordList)
                }
            }
            mGroupList.postValue(mGroupList.value)
        }
    }

}