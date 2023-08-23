package com.example.spotifycustom.model.music

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SavedSong(
    val songTitle: String,
    val songImage: String,
    val authorName: String,
    val songPreview: String
):Parcelable
