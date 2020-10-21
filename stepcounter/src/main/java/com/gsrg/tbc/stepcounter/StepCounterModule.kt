package com.gsrg.tbc.stepcounter

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
object StepCounterModule {

    @ActivityScoped
    @Provides
    fun provideStepCounter(@ApplicationContext applicationContext: Context): IStepCounter {
        return StepCounter(applicationContext)
    }
}