package com.gsrg.tbc.ui.fragments.challenges.challengedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrg.tbc.core.models.ChallengeType
import com.gsrg.tbc.stepcounter.IStepCounter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChallengeDetailsViewModel
@ViewModelInject constructor(
    private val stepCounter: IStepCounter
) : ViewModel() {

    val stepsLiveData = MutableLiveData<Int>()
    val distanceLiveData = MutableLiveData<Int>()

    fun requestProgress(challengeType: String) {
        if (challengeType == ChallengeType.TYPE_STEP) {
            requestSteps()
        } else {
            requestDistance()
        }
    }

    private fun requestSteps() {
        viewModelScope.launch {
            stepCounter.steps().collect { steps: Int ->
                stepsLiveData.value = steps
            }
        }
    }

    private fun requestDistance() {
        viewModelScope.launch {
            stepCounter.distanceInMeters().collect { meters: Int ->
                distanceLiveData.value = meters
            }
        }
    }

    fun fitnessOptions() = stepCounter.fitnessOptions()
}