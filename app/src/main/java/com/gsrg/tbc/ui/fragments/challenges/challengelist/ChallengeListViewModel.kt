package com.gsrg.tbc.ui.fragments.challenges.challengelist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrg.tbc.core.models.Challenge
import com.gsrg.tbc.core.utils.Result
import com.gsrg.tbc.domain.repository.IChallengeRepository
import com.gsrg.tbc.utils.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChallengeListViewModel
@ViewModelInject constructor(
    private val repository: IChallengeRepository
) : ViewModel() {

    val requestEventLiveData = MutableLiveData<Event<Result<*>>>()
    val challengeListLiveData = MutableLiveData<List<Challenge>>()

    private var firstRun = true

    /**
     * Request a list of [Challenge]
     * [firstRun] is used in case a view wants to make a request as soon as it appears,
     * without user interaction
     */
    fun requestChallengeList(firstRun: Boolean = false) {
        if (!firstRun || (firstRun && this.firstRun)) {
            this.firstRun = false
            viewModelScope.launch {
                repository.getChallengeList(
                ).collect { result: Result<List<Challenge>> ->
                    when (result) {
                        is Result.Success -> {
                            challengeListLiveData.value = result.data
                            requestEventLiveData.value = Event(result)
                        }
                        is Result.Loading -> {
                            result.data?.let {
                                challengeListLiveData.value = it
                            }
                            requestEventLiveData.value = Event(result)
                        }
                        is Result.Error -> {
                            requestEventLiveData.value = Event(result)
                        }
                    }
                }
            }
        }
    }
}