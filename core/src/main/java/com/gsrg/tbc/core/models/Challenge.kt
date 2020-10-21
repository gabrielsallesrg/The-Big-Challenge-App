package com.gsrg.tbc.core.models

import androidx.room.Entity

@Entity(tableName = "challengeTable", primaryKeys = ["id"])
data class Challenge(
    val id: String,
    val title: String,
    val description: String,
    val type: String,
    val goal: Int
)

object ChallengeType {
    const val TYPE_STEP = "step"
    const val TYPE_WALKING_DISTANCE = "walking_distance"
    const val TYPE_RUNNING_DISTANCE = "running_distance"
}