package com.gsrg.tbc.database.di

import android.content.Context
import com.gsrg.tbc.database.ITbcDatabase
import com.gsrg.tbc.database.TbcDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideTbcDatabase(@ApplicationContext applicationContext: Context): ITbcDatabase {
        return TbcDatabase.getInstance(applicationContext)
    }
}