package bzh.caerwent.learnwords.configure.ui

import android.media.MediaRecorder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bzh.caerwent.learnwords.common.data.MediaManager
import bzh.caerwent.learnwords.common.data.WordsListProvider
import bzh.caerwent.learnwords.common.ui.PlaySoundViewModel

class ConfRecordWordViewModel(theIdent: String, theGroupIdent: String) : PlaySoundViewModel(theIdent, theGroupIdent) {

    var mRecorder: MediaRecorder? = null


    var mIsRecordable = MutableLiveData<Boolean>()
    fun isRecordable(): LiveData<Boolean> {
        return mIsRecordable
    }

    var mIsRecording = MutableLiveData<Boolean>()
    fun isRecording(): LiveData<Boolean> {
        return mIsRecording
    }


    init {
        mIdent.value = theIdent
        mGroupIdent.value = theGroupIdent
        mRecorder?.release()
        mPlayer?.release()

        mIsRecordable.value = true
        mIsRecording.value = false
        mIsPlayable.value = WordsListProvider.INSTANCE.isFileLoaded(theGroupIdent, theIdent)
        mIsPlaying.value = false

    }


    fun record() {
        if (mIsPlaying.value ?: false) {
            mPlayer?.release()
            mIsPlaying.value = false
        }

        if (mIsRecordable?.value ?: false) {
            mRecorder?.release()
            mRecorder = MediaManager.INSTANCE.startRecord(mIdent.value!!, mGroupIdent.value!!)
            mIsRecording.value = true
            mIsPlayable.value = false
            mRecorder?.setOnInfoListener { mediaRecorder, what, extra ->
                if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    stopRecord()

                }
            }

        }
    }

    fun stopOrRecord() {
        if (mIsRecording.value ?: false) {
            stopRecord()
        } else {
            record()
        }
    }

    fun stopRecord() {
        mRecorder?.stop()
        mIsRecording.value = false

        if (WordsListProvider.INSTANCE.isFileExists(mGroupIdent.value!!, mIdent.value!!)) {
            mIsPlayable.value = true
            WordsListProvider.INSTANCE.loadList() // force to reload
        } else {
            mIsPlayable.value = false
        }
    }


    override fun onCleared() {
        super.onCleared()
        mPlayer?.release()
    }


}
