package com.example.spotifycustom.ui.searchMusic

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.spotifycustom.R
import com.example.spotifycustom.databinding.FragmentSearchMusicBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchMusicFragment : Fragment(R.layout.fragment_search_music) {

    private val viewModel by viewModels<SearchMusicViewModel>()
    private val binding by viewBinding(FragmentSearchMusicBinding::bind)
    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvSearchMusic.adapter = adapter
            searchEditText.setOnEditorActionListener { _, actionId, _ ->
                onPerformSearchAction(searchEditText.text.toString(), actionId)
                true
            }
        }



        viewModel.songsData.observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        lifecycleScope.launch {
            val selectedSongPreview = viewModel.onNavigateToSelectedSongEvent.receive().preview
            val toSave = viewModel.getDataForSave(viewModel.songsData.value)
            findNavController().navigate(
                SearchMusicFragmentDirections.actionSearchMusicFragment2ToMusicDetailedFragment(
                    selectedSongPreview,
                    toSave
                )
            )
        }
//        viewModel.clickedSongData.observe(viewLifecycleOwner) {
//            with(mediaPlayer) {
//                if (isPlaying) {
//                    stop()
//                    reset()
//                    setDataSource(it.preview)
//                    prepare()
//                    start()
//                    MusicNotification.createNotification(
//                        this@SearchMusicFragment.requireContext(),
//                        it,
//                        R.drawable.baseline_pause_circle_filled_24
//                    )
//                } else {
//                    setDataSource(it.preview)
//                    prepare()
//                    start()
//                    MusicNotification.createNotification(
//                        this@SearchMusicFragment.requireContext(),
//                        it,
//                        R.drawable.baseline_play_circle_24
//                    )
//                }
//            }
//
//
////            Intent(requireContext(), MusicService::class.java).also {
////                it.action = MusicService.Actions.START.toString()
////                requireContext().startService(it)
////            }
//
//        }
    }

    private fun onPerformSearchAction(searchQuery: String, actionId: Int) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            viewModel.checkStringAndGetSongs(searchQuery)
        }
    }
}