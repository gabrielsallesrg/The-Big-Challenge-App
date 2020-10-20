package com.gsrg.tbc.ui.fragments.challenges.challengedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrg.tbc.stepcounter.IStepCounter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChallengeDetailsViewModel
@ViewModelInject constructor(
    private val stepCounter: IStepCounter
) : ViewModel() {

    val stepsLiveData = MutableLiveData<Int>()

    fun updateSteps() {
        viewModelScope.launch {
            stepCounter.steps().collect { steps: Int ->
                stepsLiveData.value = steps
            }
        }
    }

    fun fitnessOptions() = stepCounter.fitnessOptions()
}