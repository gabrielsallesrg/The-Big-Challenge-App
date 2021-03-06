package com.gsrg.tbc.stepcounter

import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
import kotlinx.coroutines.flow.Flow

interface IStepCounter {

    fun steps(): Flow<Int>
    fun walkingDistanceInMeters(): Flow<Int>
    fun runningDistanceInMeters(): Flow<Int>
    fun fitnessOptions(): GoogleSignInOptionsExtension
}