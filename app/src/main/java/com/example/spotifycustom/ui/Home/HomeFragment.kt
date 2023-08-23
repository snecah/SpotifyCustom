package com.example.spotifycustom.ui.Home

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.spotifycustom.R
import com.example.spotifycustom.databinding.FragmentHomeBinding
import com.example.spotifycustom.model.Artists.ArtistItem
import com.example.spotifycustom.model.groupieItem.TopArtistItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter
            bottomMenu.setupWithNavController(findNavController())
            artists.setOnClickListener {
                viewModel.getTop20()
            }
        }


        viewModel.topArtist.observe(viewLifecycleOwner) { list ->
            adapter.update(list.map{
                TopArtistItem(it)
            })
        }
    }
}