package com.example.spotifycustom.model.music

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SavedSong(
    val trackTitle: String,
    val songImage: String,
    val artistName: String,
    val trackPreview: String
):Parcelable
