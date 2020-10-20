package com.gsrg.tbc.network.repository

import com.gsrg.tbc.core.models.Challenge
import com.gsrg.tbc.core.utils.Result
import com.gsrg.tbc.core.utils.TAG
import com.gsrg.tbc.database.challenge.ChallengeDao
import com.gsrg.tbc.domain.api.TbcApiService
import com.gsrg.tbc.domain.model.ChallengeListResponse
import com.gsrg.tbc.domain.repository.IChallengeRepository
import com.gsrg.tbc.network.mapping.ChallengeMapper
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
    private val challengeDao: ChallengeDao,
    private val mapper: ChallengeMapper
) : IChallengeRepository {

    /**
     * Get a list of [Challenge] using the concept of
     * Single Source of Truth (database is the source in this case)
     */
    override fun getChallengeList(): Flow<Result<List<Challenge>>> = flow {
        emit(Result.Loading(requestChallengeListFromDB()))
        requestChallengeListFromApi()
            .map { response: Result<ChallengeListResponse> -> mapper.mapFrom(response) }
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

    /**
     * Makes a request to [TbcApiService] to get a [ChallengeListResponse]
     */
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

    /**
     * Clear [Challenge] Table and insert new items
     */
    private suspend fun storeChallengeListInDB(challengeList: List<Challenge>) {
        challengeDao.clearTable()
        challengeDao.insertAll(challengeList)
    }

    /**
     * Get list of [Challenge] from DB
     */
    private suspend fun requestChallengeListFromDB(): List<Challenge> {
        return challengeDao.selectAll() ?: emptyList()
    }
}