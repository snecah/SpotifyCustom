package com.example.spotifycustom.model.music

data class Song(
    val album: AlbumX,
    val artist: ArtistX,
    val available_countries: List<String>,
    val bpm: Int,
    val contributors: List<Contributor>,
    val disk_number: Int,
    val duration: Int,
    val explicit_content_cover: Int,
    val explicit_content_lyrics: Int,
    val explicit_lyrics: Boolean,
    val gain: Double,
    val id: Int,
    val isrc: String,
    val link: String,
    val md5_image: String,
    val preview: String,
    val rank: Int,
    val readable: Boolean,
    val release_date: String,
    val share: String,
    val title: String,
    val title_short: String,
    val title_version: String,
    val track_position: Int,
    val type: String
)