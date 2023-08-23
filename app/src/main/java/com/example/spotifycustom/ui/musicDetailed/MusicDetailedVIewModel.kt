package com.example.spotifycustom.ui.musicDetailed

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import com.example.spotifycustom.model.music.SavedSong
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicDetailedVIewModel @Inject constructor() : ViewModel() {

    var currentSong = emptySave()
    var allSongsData: Array<SavedSong>? = null
    val mediaPlayer = MediaPlayer()

    fun findSong(
        allSongsData: Array<SavedSong>?,
        songPreview: String
    ): SavedSong {
        var j = 0
        return if (allSongsData.isNullOrEmpty()) emptySave()
        else {
            for (i in allSongsData.indices) {
                if (allSongsData[i].songPreview == songPreview)
                    j = i
            }
            return allSongsData[j]
        }
    }

    private fun emptySave() = SavedSong("empty", "empty", "empty", "empty")
    fun getTime(inWholeSeconds: Long): CharSequence {
        val minutes = inWholeSeconds / 60
        val seconds = inWholeSeconds % 60
        return if (seconds < 10) {
            "$minutes:0$seconds"
        } else "$minutes:$seconds"
    }
}