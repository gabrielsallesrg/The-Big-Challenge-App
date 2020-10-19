package com.gsrg.tbc.domain.api

import com.gsrg.tbc.domain.model.ChallengeListResponse
import retrofit2.Response
import retrofit2.http.GET

interface TbcApiService {

    @GET("goals")
    suspend fun getChallengeList(): Response<ChallengeListResponse>
}



