package com.gsrg.tbc.network.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gsrg.tbc.core.models.Challenge
import com.gsrg.tbc.core.utils.Result
import com.gsrg.tbc.database.challenge.ChallengeDao
import com.gsrg.tbc.domain.api.TbcApiService
import com.gsrg.tbc.domain.model.ChallengeListResponse
import com.gsrg.tbc.domain.model.ChallengeResponse
import com.gsrg.tbc.network.mapping.ChallengeMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ChallengeRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var repository: ChallengeRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun apiServiceSuccessShouldReturnList() = runBlocking {
        repository = ChallengeRepository(
            apiService = mockApiServiceSuccess(),
            challengeDao = mockChallengeDao(),
            mapper = ChallengeMapper()
        )
        repository.getChallengeList().collect {
            when (it) {
                is Result.Success -> {
                    Assert.assertEquals(listOfChallenges(), it.data)
                }
                is Result.Loading -> {
                    Assert.assertEquals(listOfChallenges(), it.data)
                }
                is Result.Error -> {
                    // Should never happen
                    Assert.assertTrue(false)
                }
            }
        }
    }

    @Test
    fun apiServiceErrorShouldReturnError() = runBlocking {
        repository = ChallengeRepository(
            apiService = mockApiServiceError(),
            challengeDao = mockChallengeDao(),
            mapper = ChallengeMapper()
        )
        repository.getChallengeList().collect {
            when (it) {
                is Result.Success -> {
                    // Should never happen
                    Assert.assertTrue(false)
                }
                is Result.Loading -> {
                    Assert.assertEquals(listOfChallenges(), it.data)
                }
                is Result.Error -> {
                    Assert.assertTrue((it.exception.message ?: "").contains("400"))
                }
            }
        }
    }

    private fun mockApiServiceSuccess(): TbcApiService {
        return object : TbcApiService {
            override suspend fun getChallengeList(): Response<ChallengeListResponse> {
                return Response.success(
                    200, ChallengeListResponse(
                        items = listOf(
                            ChallengeResponse(
                                id = ID_1,
                                title = TITLE_1,
                                description = DESCRIPTION_1,
                                type = TYPE_1,
                                goal = GOAL_1
                            ),
                            ChallengeResponse(
                                id = ID_2,
                                title = TITLE_2,
                                description = DESCRIPTION_2,
                                type = TYPE_2,
                                goal = GOAL_2
                            )
                        )
                    )
                )
            }

        }
    }

    private fun mockApiServiceError(): TbcApiService {
        return object : TbcApiService {
            override suspend fun getChallengeList(): Response<ChallengeListResponse> {
                return responseError()
            }

        }
    }

    private fun responseError(): Response<ChallengeListResponse> {
        return Response.error(400, "Error".toResponseBody())
    }

    private fun mockChallengeDao(): ChallengeDao {
        return object : ChallengeDao {
            override suspend fun insertAll(challengeList: List<Challenge>) {
            }

            override suspend fun selectAll(): List<Challenge>? {
                return listOfChallenges()
            }

            override suspend fun clearTable() {
            }

        }
    }

    private fun listOfChallenges(): List<Challenge> =
        listOf(
            Challenge(
                id = ID_1,
                title = TITLE_1,
                description = DESCRIPTION_1,
                type = TYPE_1,
                goal = GOAL_1
            ),
            Challenge(
                id = ID_2,
                title = TITLE_2,
                description = DESCRIPTION_2,
                type = TYPE_2,
                goal = GOAL_2
            )
        )

    companion object {
        const val ID_1 = "id_1"
        const val TITLE_1 = "title_1"
        const val DESCRIPTION_1 = "description_1"
        const val TYPE_1 = "type_1"
        const val GOAL_1 = 100

        const val ID_2 = "id_2"
        const val TITLE_2 = "title_2"
        const val DESCRIPTION_2 = "description_2"
        const val TYPE_2 = "type_2"
        const val GOAL_2 = 200
    }
}