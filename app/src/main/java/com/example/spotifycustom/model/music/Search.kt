package com.example.spotifycustom.model.music

data class Search(
    val `data`: List<Data>,
    val next: String,
    val total: Int
)