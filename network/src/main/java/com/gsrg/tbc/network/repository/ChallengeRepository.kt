package com.gsrg.tbc.network.repository

import com.gsrg.tbc.core.models.Challenge
import com.gsrg.tbc.core.utils.Result
import com.gsrg.tbc.core.utils.TAG
import com.gsrg.tbc.database.ITbcDatabase
import com.gsrg.tbc.domain.api.TbcApiService
import com.gsrg.tbc.domain.model.ChallengeListResponse
import com.gsrg.tbc.domain.repository.IChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class ChallengeRepository
@Inject constructor(
    private val apiService: TbcApiService,
    private val database: ITbcDatabase
) : IChallengeRepository {

    override fun getChallengeList(): Flow<Result<List<Challenge>>> = flow {
        emit(Result.Loading(requestChallengeListFromDB()))
        requestChallengeListFromApi()
            .map { response: Result<ChallengeListResponse> -> mapChallengeListResponseToListOfResponse(response) }
            .collect {
                when (it) {
                    is Result.Success -> {
                        storeChallengeListInDB(it.data)
                        emit(Result.Success(requestChallengeListFromDB()))
                    }
                    else -> {
                        Timber.tag(TAG()).e((it as Result.Error).run { exception.message ?: message })
                        emit(it)
                    }
                }
            }
    }

    private fun mapChallengeListResponseToListOfResponse(response: Result<ChallengeListResponse>): Result<List<Challenge>> {
        return when (response) {
            is Result.Success -> {
                return Result.Success(data = response.data.items.map {
                    Challenge(
                        id = it.id,
                        title = it.title,
                        description = it.description,
                        type = it.type,
                        goal = it.goal,
                        rewardTrophy = it.reward.trophy,
                        rewardPoints = it.reward.points
                    )
                })
            }
            is Result.Error -> response
            is Result.Loading -> Result.Error(Exception("This should never happen"))
        }
    }

    private fun requestChallengeListFromApi(): Flow<Result<ChallengeListResponse>> = flow {
        emit(try {
            apiService.getChallengeList().run {
                Timber.tag(this@ChallengeRepository.TAG()).d("%s -> %s", message(), body())
                if (isSuccessful) {
                    if (body() != null) {
                        Result.Success(data = body()!!)
                    } else {
                        Result.Error(Exception("Missing data"))
                    }
                } else {
                    Result.Error(Exception("${code()} ${message()}"))
                }
            }
        } catch (e: UnknownHostException) {
            Timber.tag(TAG()).e(e)
            Result.Error(e, "Something went wrong. Check your internet connection.")
        } catch (e: Exception) {
            Timber.tag(TAG()).e(e)
            Result.Error(e)
        })
    }

    private suspend fun storeChallengeListInDB(challengeList: List<Challenge>) {
        database.challengeDao().clearTable()
        database.challengeDao().insertAll(challengeList)
    }

    private suspend fun requestChallengeListFromDB(): List<Challenge> {
        return database.challengeDao().selectAll() ?: emptyList()
    }
}