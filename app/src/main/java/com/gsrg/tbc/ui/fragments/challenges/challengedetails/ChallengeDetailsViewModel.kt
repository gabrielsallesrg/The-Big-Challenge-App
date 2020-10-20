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
    val walkingDistanceLiveData = MutableLiveData<Int>()
    val runningDistanceLiveData = MutableLiveData<Int>()

    fun requestProgress(challengeType: String) {
        when (challengeType) {
            ChallengeType.TYPE_STEP -> requestSteps()
            ChallengeType.TYPE_WALKING_DISTANCE -> requestDistance()
            ChallengeType.TYPE_RUNNING_DISTANCE -> requestRunningDistance()
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
            stepCounter.walkingDistanceInMeters().collect { meters: Int ->
                walkingDistanceLiveData.value = meters
            }
        }
    }

    private fun requestRunningDistance() {
        viewModelScope.launch {
            stepCounter.runningDistanceInMeters().collect { meters: Int ->
                runningDistanceLiveData.value = meters
            }
        }
    }

    fun fitnessOptions() = stepCounter.fitnessOptions()
}