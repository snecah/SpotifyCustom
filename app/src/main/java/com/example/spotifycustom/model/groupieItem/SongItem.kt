package com.example.spotifycustom.model.groupieItem

import android.view.View
import com.bumptech.glide.Glide
import com.example.spotifycustom.R
import com.example.spotifycustom.databinding.SongItemBinding
import com.example.spotifycustom.model.music.Data
import com.xwray.groupie.viewbinding.BindableItem

class SongItem(val data: Data, val onSongItemClicked: (Data) -> Unit) :
    BindableItem<SongItemBinding>() {
    override fun bind(binding: SongItemBinding, position: Int) {
        with(binding) {
            binding.songName.text = data.title
            binding.songAuthor.text = data.artist.name
            Glide.with(songPicture.context).load(data.artist.picture_small).into(songPicture)

            songPicture.setOnClickListener {
                onSongItemClicked(data)
            }
        }
    }

    override fun getLayout() = R.layout.song_item

    override fun initializeViewBinding(view: View) = SongItemBinding.bind(view)
}