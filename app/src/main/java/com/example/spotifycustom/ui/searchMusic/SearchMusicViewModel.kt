package com.example.spotifycustom.ui.searchMusic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifycustom.model.groupieItem.SongItem
import com.example.spotifycustom.model.music.Data
import com.example.spotifycustom.model.music.SavedSong
import com.example.spotifycustom.repository.SpotifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMusicViewModel @Inject constructor(private val repository: SpotifyRepository) :
    ViewModel() {

    private val _songsData = MutableLiveData<List<SongItem>>()
    val songsData: LiveData<List<SongItem>>
        get() = _songsData

    private val _clickedSongData = MutableLiveData<Data>()
    val clickedSongData: LiveData<Data>
        get() = _clickedSongData

    val onNavigateToSelectedSongEvent = Channel<Data>()


    fun checkStringAndGetSongs(inputString: String) {
        if (inputString.isEmpty()) {
        } else {
            getSongs(inputString)
        }
    }

    fun getDataForSave(songsData: List<SongItem>?): Array<SavedSong>? {
        val savedData = songsData?.map { songItem ->
            SavedSong(
                songItem.data.title,
                songItem.data.artist.picture_small,
                songItem.data.artist.name,
                songItem.data.preview
            )
        }?.toTypedArray()
        return savedData
    }

    private fun getSongs(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val items = repository.getSong(keyword).data
            _songsData.postValue(items.map { it ->
                SongItem(it, onSongItemClickedAction())
            })
        }
    }

    private fun onSongItemClickedAction(): (Data) -> Unit = { selectedSong ->
        _clickedSongData.value = selectedSong
        viewModelScope.launch {
            onNavigateToSelectedSongEvent.send(selectedSong)
        }
    }
}