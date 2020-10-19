package com.gsrg.tbc.ui.fragments.challenges.challengelist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.gsrg.tbc.domain.repository.IChallengeRepository

class ChallengeListViewModel
@ViewModelInject constructor(
    private val repository: IChallengeRepository
) : ViewModel()