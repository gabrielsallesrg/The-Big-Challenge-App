package com.gsrg.tbc.network.di

import com.gsrg.tbc.network.mapping.ChallengeMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
object MapperModule {

    @Provides
    @ActivityScoped
    fun provideChallengeMapper(): ChallengeMapper = ChallengeMapper()
}