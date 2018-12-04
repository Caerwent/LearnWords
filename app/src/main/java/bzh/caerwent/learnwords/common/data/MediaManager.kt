package bzh.caerwent.learnwords.common.data

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import java.io.File
import java.io.IOException


class MediaManager {

    companion object {
        var TAG = MediaManager.javaClass.simpleName
        var EXT = ".3gp"
        var INSTANCE = MediaManager()
            private set
    }

    fun startRecord(ident: String, groupIdent: String): MediaRecorder? {

        var mainDir = File(WordsListProvider.STORAGE_LOCATION + groupIdent)
        if (!mainDir.exists()) {
            mainDir.mkdirs();
        }

        var audioRecorder: MediaRecorder
        var outputFile = WordsListProvider.STORAGE_LOCATION + "$groupIdent/$ident$EXT"

        audioRecorder = MediaRecorder()
        audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        audioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
        audioRecorder.setOutputFile(outputFile)

        try {
            audioRecorder.prepare()
            audioRecorder.start()
        } catch (ise: IllegalStateException) {
            Log.e(TAG, "IllegalStateException when starting record " + ise.message)
            return null
        } catch (ioe: IOException) {
            Log.e(TAG, "IOException when starting record " + ioe.message)
            return null
        }

        return audioRecorder
    }

    fun startPlay(ident: String, groupIdent: String): MediaPlayer? {

        val mediaPlayer = MediaPlayer()
        var outputFile = WordsListProvider.STORAGE_LOCATION + "$groupIdent/$ident.3gp"
        try {
            mediaPlayer.setDataSource(outputFile);
            mediaPlayer.setVolume(1.0F, 1.0F)
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (e: Exception) {
            Log.e(TAG, "Exception when playing record " + e.message)
            return null
        }

        return mediaPlayer
    }


}