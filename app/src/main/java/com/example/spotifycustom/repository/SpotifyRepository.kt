package com.example.spotifycustom.repository

import com.example.spotifycustom.Constants
import com.example.spotifycustom.model.Artists.ArtistItem
import com.example.spotifycustom.model.Artists.Artists
import com.example.spotifycustom.model.Artists.AvatarImage
import com.example.spotifycustom.model.music.Search
import com.example.spotifycustom.retrofit.SpotifyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifyRepository @Inject constructor(private val spotifyApi: SpotifyApi) {
    suspend fun getTop20ArtistsByMonthlyListeners(): List<ArtistItem> =
        spotifyApi.getTop20ArtistByMonthlyListeners()

    suspend fun getArtistProfileImage(keyword: String): Artists = spotifyApi.getArtistImage(keyword)

    suspend fun getSong(keyword: String): Search =
        spotifyApi.getSongFromDeezerApi(Constants.DEEZER_URL, keyword)
}