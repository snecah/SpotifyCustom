package com.example.spotifycustom.ui.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifycustom.model.Artists.ArtistItem
import com.example.spotifycustom.model.Artists.ArtistItemWithAvatar
import com.example.spotifycustom.model.Artists.AvatarImage
import com.example.spotifycustom.repository.SpotifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val spotifyRepository: SpotifyRepository):ViewModel() {

    private val _topArtists = MutableLiveData<List<ArtistItem>>()
    val topArtist: LiveData<List<ArtistItem>>
        get() = _topArtists

    fun getTop20() {
        viewModelScope.launch {
            val top50 = spotifyRepository.getTop20ArtistsByMonthlyListeners()
//            val top20ProfileImages = ArrayList<AvatarImage>()
//            top20.forEach {artistItem ->
//                top20ProfileImages.add(spotifyRepository.getArtistProfileImage(artistItem.artist).items[0].data.visuals.avatarImage)
//            }
//            val top20WithImages = top20.zip(top20ProfileImages).map {
//                ArtistItemWithAvatar(it.first, it.second)
//            }
            _topArtists.postValue(top50)
        }
    }
}