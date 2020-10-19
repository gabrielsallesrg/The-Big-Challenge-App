package com.gsrg.tbc.database.challenge

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.gsrg.tbc.core.models.Challenge
import com.gsrg.tbc.database.TbcDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class ChallengeDaoTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var dao: ChallengeDao
    private lateinit var database: TbcDatabase

    @Before
    fun createDB() {
        Dispatchers.setMain(testDispatcher)
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, TbcDatabase::class.java
        ).build()
        dao = database.challengeDao()
    }

    @After
    @Throws(IOException::class)
    fun deleteDataAndCloseDatabase() {
        database.close()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun insertOneItemInDB() = runBlocking {
        val challenge = challengeCreator()
        dao.insertAll(listOf(challenge))
        val challengeList = dao.selectAll()!!
        Assert.assertEquals(1, challengeList.size)
    }


    @Test
    fun insertOneItemInDBAndThenRemove() = runBlocking {
        val challenge = challengeCreator()
        dao.insertAll(listOf(challenge))
        dao.clearTable()
        val challengeList = dao.selectAll()!!
        Assert.assertEquals(0, challengeList.size)
    }

    @Test
    fun insertMultipleItemsInDB() = runBlocking {
        val challenge0 = challengeCreator("1")
        val challenge1 = challengeCreator("2")
        val challenge2 = challengeCreator("3")
        val challenge3 = challengeCreator("4")
        val challenge4 = challengeCreator("5")

        dao.insertAll(listOf(challenge0, challenge1, challenge2, challenge3, challenge4))
        var challengeList = dao.selectAll()!!

        Assert.assertEquals(5, challengeList.size)
        Assert.assertEquals(challenge0.id, challengeList[0].id)
        Assert.assertEquals(challenge1.id, challengeList[1].id)
        Assert.assertEquals(challenge2, challengeList[2])
        Assert.assertEquals(challenge3.id, challengeList[3].id)
        Assert.assertEquals(challenge4.id, challengeList[4].id)

        dao.clearTable()
        challengeList = dao.selectAll()!!
        Assert.assertEquals(0, challengeList.size)
    }

    private fun challengeCreator(
        id: String = "",
        title: String = "",
        description: String = "",
        type: String = "",
        goal: Int = 0,
        rewardTrophy: String = "",
        rewardPoints: Int = 0
    ): Challenge {
        return Challenge(
            id = id,
            title = title,
            description = description,
            type = type, goal = goal,
            rewardTrophy = rewardTrophy,
            rewardPoints = rewardPoints
        )
    }
}