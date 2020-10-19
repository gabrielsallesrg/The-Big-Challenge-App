package com.gsrg.tbc.domain.model

import com.google.gson.annotations.SerializedName

data class ChallengeListResponse(
    @field:SerializedName("items") val items: List<ChallengeResponse>,
    @field:SerializedName("nextPageToken") val nextPageToken: String
)