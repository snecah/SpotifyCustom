package com.example.spotifycustom.model.groupieItem

import android.view.View
import com.bumptech.glide.Glide
import com.example.spotifycustom.R
import com.example.spotifycustom.databinding.ArtistsItemBinding
import com.example.spotifycustom.model.Artists.ArtistItem
import com.example.spotifycustom.model.Artists.ArtistItemWithAvatar
import com.xwray.groupie.viewbinding.BindableItem

class TopArtistItem(val artistItem: ArtistItem) :
    BindableItem<ArtistsItemBinding>() {
    override fun bind(binding: ArtistsItemBinding, position: Int) {
        with(binding) {
            artistName.text = artistItem.artist
            artistListeners.text = (artistItem.monthlyListeners * 1000).toString()
        }
    }

    override fun getLayout() = R.layout.artists_item

    override fun initializeViewBinding(view: View): ArtistsItemBinding =
        ArtistsItemBinding.bind(view)
}
