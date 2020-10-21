package com.gsrg.tbc.network.di

import android.content.Context
import com.gsrg.tbc.domain.api.TbcApiService
import com.gsrg.tbc.network.BuildConfig
import com.gsrg.tbc.network.MockInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideClient(@ApplicationContext applicationContext: Context): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder().apply {
            addInterceptor(
                if (BuildConfig.MOCK_RESPONSE) {
                    MockInterceptor(applicationContext)
                } else {
                    logger
                }
            )
        }.build()
    }

    @Singleton
    @Provides
    fun provideTbcApiService(client: OkHttpClient): TbcApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.TBC_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TbcApiService::class.java)
    }
}