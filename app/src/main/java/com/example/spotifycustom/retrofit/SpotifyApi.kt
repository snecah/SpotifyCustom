package com.example.spotifycustom.retrofit

import com.example.spotifycustom.model.Artists.ArtistItem
import com.example.spotifycustom.model.Artists.Artists
import com.example.spotifycustom.model.music.Search
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface SpotifyApi {
    @Headers(
        "X-RapidAPI-Key:80c52a7d2bmsh3d15f1bdea91c4dp1197b6jsn213c90b99701",
        "X-RapidAPI-Host:spotify81.p.rapidapi.com"
    )
    @GET("top_20_by_monthly_listeners")
    suspend fun getTop20ArtistByMonthlyListeners(): List<ArtistItem>

    @Headers(
        "X-RapidAPI-Key:80c52a7d2bmsh3d15f1bdea91c4dp1197b6jsn213c90b99701",
        "X-RapidAPI-Host:spotify81.p.rapidapi.com"
    )
    @GET("search")
    suspend fun getArtistImage(
        @Query("q") keyword: String,
        @Query("type") type: String = "artists"
    ): Artists


    @Headers(
        "X-RapidAPI-Key:80c52a7d2bmsh3d15f1bdea91c4dp1197b6jsn213c90b99701",
        "X-RapidAPI-Host:deezerdevs-deezer.p.rapidapi.com"
    )
    @GET
    suspend fun getSongFromDeezerApi(@Url url: String, @Query("q") keyword: String):Search

}