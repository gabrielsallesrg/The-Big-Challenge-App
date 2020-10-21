package com.gsrg.tbc.ui.fragments.challenges.challengelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gsrg.tbc.core.models.Challenge
import com.gsrg.tbc.core.utils.Result
import com.gsrg.tbc.domain.repository.IChallengeRepository
import com.gsrg.tbc.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class ChallengeListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: ChallengeListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun shouldUpdateLiveDataWithSuccess() {
        viewModel = ChallengeListViewModel(repository = mockChallengeRepositorySuccess())
        viewModel.requestChallengeList()

        Assert.assertEquals(listOfChallenges(), viewModel.challengeListLiveData.value)
        Assert.assertTrue(viewModel.requestEventLiveData.value is Event)
        Assert.assertTrue((viewModel.requestEventLiveData.value as Event).peekContent() is Result.Success)
    }

    @Test
    fun shouldUpdateLiveDataWithError() {
        viewModel = ChallengeListViewModel(repository = mockChallengeRepositoryError())
        viewModel.requestChallengeList()

        Assert.assertEquals(null, viewModel.challengeListLiveData.value)
        Assert.assertTrue(viewModel.requestEventLiveData.value is Event)
        Assert.assertTrue((viewModel.requestEventLiveData.value as Event).peekContent() is Result.Error)
    }

    @Test
    fun shouldUpdateLiveDataWithLoadingAndData() {
        viewModel = ChallengeListViewModel(repository = mockChallengeRepositoryLoadingWithData())
        viewModel.requestChallengeList()

        Assert.assertEquals(listOfChallenges(), viewModel.challengeListLiveData.value)
        Assert.assertTrue(viewModel.requestEventLiveData.value is Event)
        Assert.assertTrue((viewModel.requestEventLiveData.value as Event).peekContent() is Result.Loading)
    }

    @Test
    fun shouldUpdateLiveDataWithLoadingAndNoData() {
        viewModel = ChallengeListViewModel(repository = mockChallengeRepositoryLoadingWithoutData())
        viewModel.requestChallengeList()

        Assert.assertEquals(null, viewModel.challengeListLiveData.value)
        Assert.assertTrue(viewModel.requestEventLiveData.value is Event)
        Assert.assertTrue((viewModel.requestEventLiveData.value as Event).peekContent() is Result.Loading)
    }


    private fun mockChallengeRepositorySuccess(): IChallengeRepository {
        return object : IChallengeRepository {
            override fun getChallengeList(): Flow<Result<List<Challenge>>> = flow {
                emit(Result.Success(data = listOfChallenges()))
            }
        }
    }

    private fun mockChallengeRepositoryError(): IChallengeRepository {
        return object : IChallengeRepository {
            override fun getChallengeList(): Flow<Result<List<Challenge>>> = flow {
                emit(Result.Error(UnknownHostException("1"), "2"))
            }

        }
    }

    private fun mockChallengeRepositoryLoadingWithData(): IChallengeRepository {
        return object : IChallengeRepository {
            override fun getChallengeList(): Flow<Result<List<Challenge>>> = flow {
                emit(Result.Loading(data = listOfChallenges()))
            }

        }
    }

    private fun mockChallengeRepositoryLoadingWithoutData(): IChallengeRepository {
        return object : IChallengeRepository {
            override fun getChallengeList(): Flow<Result<List<Challenge>>> = flow {
                emit(Result.Loading(data = null))
            }

        }
    }

    private fun listOfChallenges(): List<Challenge> =
        listOf(
            Challenge(
                id = "id_1",
                title = "title_1",
                description = "description_1",
                type = "type_1",
                goal = 100
            ),
            Challenge(
                id = "id_2",
                title = "title_2",
                description = "description_2",
                type = "type_2",
                goal = 200
            )
        )
}