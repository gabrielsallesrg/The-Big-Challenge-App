package com.gsrg.tbc.ui.fragments.challenges.challengedetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.gsrg.tbc.BuildConfig
import com.gsrg.tbc.R
import com.gsrg.tbc.databinding.FragmentChallengeDetailsBinding
import com.gsrg.tbc.ui.MainActivityViewModel
import com.gsrg.tbc.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentChallengeDetailsBinding
    private val viewModel: ChallengeDetailsViewModel by viewModels()
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengeDetailsBinding.inflate(inflater, container, false)
        setUI()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservers()
        requestProgressOrPermission()
    }

    private fun setUI() {
        activityViewModel.selectedChallenge.let {
            binding.titleTextView.text = it.title
            binding.descriptionTextView.text = it.description
        }
    }

    private fun setObservers() {
        viewModel.stepsLiveData.observe(viewLifecycleOwner, { steps: Int ->
            val goal = activityViewModel.selectedChallenge.goal
            updateResultViews(current = steps, goal = goal, unit = getString(R.string.steps))
        })
        viewModel.walkingDistanceLiveData.observe(viewLifecycleOwner, { meters: Int ->
            val goal = activityViewModel.selectedChallenge.goal
            updateResultViews(current = meters, goal = goal, unit = getString(R.string.meters))
        })
        viewModel.runningDistanceLiveData.observe(viewLifecycleOwner, { meters: Int ->
            val goal = activityViewModel.selectedChallenge.goal
            updateResultViews(current = meters, goal = goal, unit = getString(R.string.meters))
        })
    }

    private fun updateResultViews(current: Int, goal: Int, unit: String) {
        binding.progressTextView.text = getString(R.string.challenge_progress, current, goal, unit)
        binding.resultTextView.text = if (isCompleted(current = current, goal = goal)) getString(R.string.congratulations) else getString(R.string.keep_going)
    }

    private fun isCompleted(current: Int, goal: Int) = current >= goal

    private fun requestProgressOrPermission() {
        val account = GoogleSignIn.getAccountForExtension(requireContext(), viewModel.fitnessOptions())
        if (!BuildConfig.MOCK_RESPONSE && !GoogleSignIn.hasPermissions(account, viewModel.fitnessOptions())) {
            GoogleSignIn.requestPermissions(
                this,
                FITNESS_PERMISSION,
                account,
                viewModel.fitnessOptions()
            )
        } else {
            viewModel.requestProgress(activityViewModel.selectedChallenge.type)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == FITNESS_PERMISSION) {
            viewModel.requestProgress(activityViewModel.selectedChallenge.type)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        private const val FITNESS_PERMISSION = 238
    }
}