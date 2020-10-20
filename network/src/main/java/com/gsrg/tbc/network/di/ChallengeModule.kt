package com.gsrg.tbc.network.di

import com.gsrg.tbc.domain.repository.IChallengeRepository
import com.gsrg.tbc.network.repository.ChallengeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
abstract class ChallengeModule {

    @Binds
    @ActivityScoped
    abstract fun bindChallengeRepository(challengeRepository: ChallengeRepository): IChallengeRepository
}