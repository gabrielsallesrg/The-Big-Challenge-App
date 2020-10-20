package com.gsrg.tbc.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.gsrg.tbc.core.models.Challenge

class MainActivityViewModel
@ViewModelInject constructor() : ViewModel() {

    lateinit var selectedChallenge: Challenge
}