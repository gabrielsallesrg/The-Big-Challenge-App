package com.gsrg.tbc.ui.fragments.challenges.challengedetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservers()
        requestSteps()
    }

    private fun setObservers() {
        viewModel.stepsLiveData.observe(viewLifecycleOwner, {
            val totalSteps = "${binding.textView.text} $it"
            binding.textView.text = totalSteps
        })
    }

    private fun requestSteps() {
        val account = GoogleSignIn.getAccountForExtension(requireContext(), viewModel.fitnessOptions())
        if (!GoogleSignIn.hasPermissions(account, viewModel.fitnessOptions())) {
            GoogleSignIn.requestPermissions(
                this,
                FITNESS_PERMISSION,
                account,
                viewModel.fitnessOptions()
            )
        } else {
            viewModel.updateSteps()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == FITNESS_PERMISSION) {
            viewModel.updateSteps()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        private const val FITNESS_PERMISSION = 238
    }
}