package com.gsrg.tbc.database

import com.gsrg.tbc.database.challenge.ChallengeDao

interface ITbcDatabase {

    fun challengeDao(): ChallengeDao
}