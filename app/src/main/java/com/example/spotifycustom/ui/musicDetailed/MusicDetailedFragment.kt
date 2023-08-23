package com.example.spotifycustom.ui.musicDetailed

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.spotifycustom.R
import com.example.spotifycustom.databinding.FragmentMusicDetailedBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@AndroidEntryPoint
class MusicDetailedFragment : Fragment(R.layout.fragment_music_detailed) {

    private val viewModel by viewModels<MusicDetailedVIewModel>()
    private val binding by viewBinding(FragmentMusicDetailedBinding::bind)
    private val player = MediaPlayer()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = MusicDetailedFragmentArgs.fromBundle(requireArguments())

        viewModel.currentSong = viewModel.findSong(args.allSongsData, args.songPreview)
        viewModel.allSongsData = args.allSongsData

        viewModel.mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        )

        player.setAudioAttributes(
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        )

        with(binding) {
            Glide.with(requireContext()).load(viewModel.currentSong.songImage).into(trackImage)
            trackTitle.text = viewModel.currentSong.songTitle
            authorName.text = viewModel.currentSong.authorName
            playImage.setImageResource(R.drawable.baseline_pause_circle_filled_24)

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        viewModel.mediaPlayer.seekTo(progress)
//                        binding.currentTime.text =
//                            viewModel.mediaPlayer.currentPosition.seconds.toString()
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })


        }
        with(viewModel.mediaPlayer) {
            if (isPlaying) {
                Toast.makeText(requireContext(), "PLAYING", Toast.LENGTH_SHORT).show()
            } else {
                setDataSource(viewModel.currentSong.songPreview)
                prepare()
                start()
            }
        }

        initializeSeekBar()

        lifecycleScope.launch(Dispatchers.Main) {
            while (viewModel.mediaPlayer.isPlaying) {
                val currentTimeInMilli = viewModel.mediaPlayer.currentPosition.milliseconds
                binding.seekBar.progress = viewModel.mediaPlayer.currentPosition
                binding.currentTime.text = viewModel.getTime(currentTimeInMilli.inWholeSeconds)
                delay(1000)
            }
        }
    }

    override fun onDestroy() {
        Toast.makeText(requireContext(), "dead", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }


    private fun initializeSeekBar() {
        with(binding) {
            seekBar.progress = 0
            seekBar.max = viewModel.mediaPlayer.duration
            timeLeft.text =
                viewModel.getTime(viewModel.mediaPlayer.duration.milliseconds.inWholeSeconds)
        }
    }
}