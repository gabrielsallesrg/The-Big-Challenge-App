package com.gsrg.tbc.ui.fragments.challenges.challengeDetails

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
import com.google.android.gms.common.api.Scope
import com.gsrg.tbc.core.models.ChallengeType
import com.gsrg.tbc.stepcounter.IStepCounter
import com.gsrg.tbc.ui.fragments.challenges.challengedetails.ChallengeDetailsViewModel
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

@ExperimentalCoroutinesApi
class ChallengeDetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: ChallengeDetailsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ChallengeDetailsViewModel(stepCounter = MockStepCounter())
    }

    @Test
    fun shouldUpdateSteps() {
        viewModel.requestProgress(ChallengeType.TYPE_STEP)
        Assert.assertEquals(5, viewModel.stepsLiveData.value)
        Assert.assertEquals(null, viewModel.walkingDistanceLiveData.value)
        Assert.assertEquals(null, viewModel.runningDistanceLiveData.value)
    }

    @Test
    fun shouldWalkingDistance() {
        viewModel.requestProgress(ChallengeType.TYPE_WALKING_DISTANCE)
        Assert.assertEquals(null, viewModel.stepsLiveData.value)
        Assert.assertEquals(100, viewModel.walkingDistanceLiveData.value)
        Assert.assertEquals(null, viewModel.runningDistanceLiveData.value)
    }

    @Test
    fun shouldRunningDistance() {
        viewModel.requestProgress(ChallengeType.TYPE_RUNNING_DISTANCE)
        Assert.assertEquals(null, viewModel.stepsLiveData.value)
        Assert.assertEquals(null, viewModel.walkingDistanceLiveData.value)
        Assert.assertEquals(500, viewModel.runningDistanceLiveData.value)
    }


    class MockStepCounter : IStepCounter {
        override fun steps(): Flow<Int> = flow { emit(5) }

        override fun walkingDistanceInMeters(): Flow<Int> = flow { emit(100) }

        override fun runningDistanceInMeters(): Flow<Int> = flow { emit(500) }

        override fun fitnessOptions(): GoogleSignInOptionsExtension {
            return object : GoogleSignInOptionsExtension {
                override fun getExtensionType(): Int = 0
                override fun toBundle(): Bundle = Bundle()
                override fun getImpliedScopes(): MutableList<Scope> = mutableListOf()
            }
        }

    }
}