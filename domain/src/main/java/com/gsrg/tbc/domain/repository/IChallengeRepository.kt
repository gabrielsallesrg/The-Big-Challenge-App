package com.gsrg.tbc.domain.repository

import com.gsrg.tbc.core.models.Challenge
import com.gsrg.tbc.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface IChallengeRepository {

    fun getChallengeList(): Flow<Result<List<Challenge>>>
}