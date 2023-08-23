package com.example.spotifycustom.ui.GetStarted

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.spotifycustom.R
import com.example.spotifycustom.databinding.FragmentGetStartedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetStartedFragment : Fragment(R.layout.fragment_get_started) {
    private val binding by viewBinding(FragmentGetStartedBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getStartedButton.setOnClickListener {
            findNavController().navigate(GetStartedFragmentDirections.actionGetStartedFragmentToHomeFragment())
        }
    }
}