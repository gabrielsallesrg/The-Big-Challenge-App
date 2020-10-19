package com.gsrg.tbc.core.models

import androidx.room.Entity

@Entity(tableName = "challengeTable", primaryKeys = ["id"])
data class Challenge(
    val id: String,
    val title: String,
    val description: String,
    val type: String,
    val goal: Int,
    val rewardTrophy: String,
    val rewardPoints: Int
)