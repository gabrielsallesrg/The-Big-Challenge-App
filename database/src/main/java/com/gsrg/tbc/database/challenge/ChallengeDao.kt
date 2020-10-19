package com.gsrg.tbc.database.challenge

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gsrg.tbc.core.models.Challenge

@Dao
interface ChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(challengeList: List<Challenge>)

    @Query("SELECT * FROM challengeTable")
    suspend fun selectAll(): List<Challenge>?

    @Query("DELETE FROM challengeTable")
    suspend fun clearTable()
}