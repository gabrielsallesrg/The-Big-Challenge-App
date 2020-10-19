package com.gsrg.tbc.domain.model

import com.google.gson.annotations.SerializedName

data class RewardResponse(
    @field:SerializedName("trophy") val trophy: String,
    @field:SerializedName("points") val points: Int
)