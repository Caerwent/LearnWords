package bzh.caerwent.learnwords.common.ui

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bzh.caerwent.learnwords.common.data.MediaManager
import bzh.caerwent.learnwords.common.data.WordsListProvider

open class PlaySoundViewModel constructor(protected var theIdent: String, protected var theGroupIdent: String) : ViewModel() {


    var mPlayer: MediaPlayer? = null


    var mIsPlayable = MutableLiveData<Boolean>()
    fun isPlayable(): LiveData<Boolean> {
        return mIsPlayable
    }

    var mIsPlaying = MutableLiveData<Boolean>()
    fun isPlaying(): LiveData<Boolean> {
        return mIsPlaying
    }

    protected var mIdent = MutableLiveData<String>()
    fun getIdent(): LiveData<String> {
        return mIdent
    }

    protected var mGroupIdent = MutableLiveData<String>()

    init {
        mIdent.value = theIdent
        mGroupIdent.value = theGroupIdent

        mPlayer?.release()

        mIsPlayable.value = WordsListProvider.INSTANCE.isFileLoaded(theGroupIdent, theIdent)
        mIsPlaying.value = false

    }


    fun getGroupIdent(): LiveData<String> {
        return mGroupIdent
    }


    fun play() {
        if (mIsPlayable?.value ?: false) {
            mPlayer?.release()
            mPlayer = MediaManager.INSTANCE.startPlay(mIdent.value!!, mGroupIdent.value!!)
            mIsPlaying.value = true
            mPlayer?.setOnCompletionListener { mIsPlaying.value = false }
        }
    }

    fun stopPlay() {
        mPlayer?.stop()
        mIsPlaying.value = false
    }

    fun stopOrPlay() {
        if (mIsPlaying.value ?: false) {
            stopPlay()
        } else {
            play()
        }
    }


    override fun onCleared() {
        mPlayer?.release()
    }


}
