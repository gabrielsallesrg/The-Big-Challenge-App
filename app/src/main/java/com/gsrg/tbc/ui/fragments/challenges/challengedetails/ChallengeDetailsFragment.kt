package com.gsrg.tbc.ui.fragments.challenges.challengedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gsrg.tbc.databinding.FragmentChallengeDetailsBinding
import com.gsrg.tbc.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentChallengeDetailsBinding
    private val viewModel: ChallengeDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
}