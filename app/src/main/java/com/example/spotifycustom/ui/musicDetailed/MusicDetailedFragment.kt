package com.example.spotifycustom.ui.musicDetailed

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.spotifycustom.MusicService
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
    private var musicService: MusicService? = null
    private var isServiceBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.LocalBinder
            musicService = binder.getService()
            isServiceBound = true

            binding.timeLeft.text = viewModel.getTime(musicService?.mediaPlayer?.duration?.milliseconds?.inWholeSeconds ?: 0)
            initializeSeekBar()
            lifecycleScope.launch(Dispatchers.Main) {
                while (musicService?.mediaPlayer?.isPlaying == true) {
                    val currentTimeInMilli = musicService?.mediaPlayer?.currentPosition?.milliseconds
                    binding.seekBar.progress = musicService?.mediaPlayer?.currentPosition ?: 0
                    binding.currentTime.text = viewModel.getTime(currentTimeInMilli?.inWholeSeconds ?: 0)
                    delay(1000)
                }
            }

            binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        musicService?.mediaPlayer?.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })


        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            isServiceBound = false
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = MusicDetailedFragmentArgs.fromBundle(requireArguments())

        viewModel.currentTrack = viewModel.findSong(args.allSongsData, args.songPreview)
        viewModel.allSongsData = args.allSongsData
        val intent = Intent(requireContext(), MusicService::class.java).also {
            it.putExtra("url", viewModel.currentTrack.trackPreview)
            it.putExtra("artistName", viewModel.currentTrack.artistName)
            it.putExtra("trackTitle", viewModel.currentTrack.trackTitle)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            requireContext().startForegroundService(intent)
        else requireContext().startService(intent)

        requireContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)


//        val player = musicService?.mediaPlayer

        with(binding) {
            Glide.with(requireContext()).load(viewModel.currentTrack.songImage).into(trackImage)
            trackTitle.text = viewModel.currentTrack.trackTitle
            authorName.text = viewModel.currentTrack.artistName
            playImage.setImageResource(R.drawable.baseline_pause_circle_filled_24)

//            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//                override fun onProgressChanged(
//                    seekBar: SeekBar?,
//                    progress: Int,
//                    fromUser: Boolean
//                ) {
//                    if (fromUser) {
//                            musicService?.mediaPlayer?.seekTo(progress)
//                    }
//                }
//
//                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
//                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
//            })

        }
//        initializeSeekBar()
//        lifecycleScope.launch(Dispatchers.Main) {
//            while (musicService?.mediaPlayer?.isPlaying == true) {
//                val currentTimeInMilli = musicService?.mediaPlayer?.currentPosition?.milliseconds
//                binding.seekBar.progress = musicService?.mediaPlayer?.currentPosition ?: 0
//                binding.currentTime.text = viewModel.getTime(currentTimeInMilli?.inWholeSeconds ?: 0)
//                delay(1000)
//            }
//        }
    }
    override fun onDestroy() {
        if (isServiceBound) {
            requireContext().unbindService(serviceConnection)
            isServiceBound = false
        }
        super.onDestroy()
    }


    private fun initializeSeekBar() {
        with(binding) {
            seekBar.progress = 0
            seekBar.max = musicService?.mediaPlayer?.duration ?: 0
            timeLeft.text =
                viewModel.getTime(musicService?.mediaPlayer?.duration?.milliseconds?.inWholeSeconds ?: 0)
        }
    }
}