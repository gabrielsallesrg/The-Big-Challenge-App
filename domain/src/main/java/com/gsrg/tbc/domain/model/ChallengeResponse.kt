package com.gsrg.tbc.domain.model

import com.google.gson.annotations.SerializedName

data class ChallengeResponse(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("description") val description: String,
    @field:SerializedName("type") val type: String,
    @field:SerializedName("goal") val goal: Int,
    @field:SerializedName("reward") val reward: RewardResponse,
)