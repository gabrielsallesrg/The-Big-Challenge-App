package com.gsrg.tbc.network.mapping

import com.gsrg.tbc.core.utils.Result
import com.gsrg.tbc.domain.model.ChallengeListResponse
import com.gsrg.tbc.domain.model.ChallengeResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class ChallengeMapperTest {

    private lateinit var mapper: ChallengeMapper

    @Before
    fun setup() {
        mapper = ChallengeMapper()
    }

    @Test
    fun errorResponseShouldReturnItself() {
        val response = Result.Error(UnknownHostException("1"), "error")
        val mappedResponse = mapper.mapFrom(response)

        Assert.assertEquals(response, mappedResponse)
        Assert.assertEquals(response.exception, (mappedResponse as Result.Error).exception)
        Assert.assertEquals(response.message, mappedResponse.message)
    }

    @Test
    fun loadingShouldNotHappenAndShouldReturnResultError() {
        val response = Result.Loading(null)
        val mappedResponse = mapper.mapFrom(response)

        Assert.assertTrue(mappedResponse is Result.Error)
    }

    @Test
    fun emptyListShouldReturnEmptyList() {
        val response = Result.Success(data = ChallengeListResponse(items = emptyList()))
        val mappedResponse = mapper.mapFrom(response)

        Assert.assertTrue(mappedResponse is Result.Success)
        Assert.assertTrue((mappedResponse as Result.Success).data.isEmpty())
    }

    @Test
    fun mapperShouldReturnNewDataObjectWithSameValues() {
        val challengeListResponse = Result.Success(
            data = ChallengeListResponse(
                items = listOf(
                    ChallengeResponse(
                        id = "id_1",
                        title = "title_1",
                        description = "description_1",
                        type = "type_1",
                        goal = 100
                    ),
                    ChallengeResponse(
                        id = "id_2",
                        title = "title_2",
                        description = "description_2",
                        type = "type_2",
                        goal = 200
                    )
                )
            )
        )
        val mapperResponse = mapper.mapFrom(challengeListResponse)
        Assert.assertTrue(mapperResponse is Result.Success)

        val originalItems = challengeListResponse.data.items
        val mappedItems = (mapperResponse as Result.Success).data
        Assert.assertEquals(originalItems.size, mappedItems.size)

        Assert.assertEquals(originalItems[0].id, mappedItems[0].id)
        Assert.assertEquals(originalItems[0].goal, mappedItems[0].goal)
        Assert.assertEquals(originalItems[0].type, mappedItems[0].type)
        Assert.assertEquals(originalItems[0].title, mappedItems[0].title)
        Assert.assertEquals(originalItems[0].description, mappedItems[0].description)

        Assert.assertEquals(originalItems[1].id, mappedItems[1].id)
        Assert.assertEquals(originalItems[1].goal, mappedItems[1].goal)
        Assert.assertEquals(originalItems[1].type, mappedItems[1].type)
        Assert.assertEquals(originalItems[1].title, mappedItems[1].title)
        Assert.assertEquals(originalItems[1].description, mappedItems[1].description)

        Assert.assertNotEquals(originalItems[0].id, mappedItems[1].id)
        Assert.assertNotEquals(originalItems[0].goal, mappedItems[1].goal)
        Assert.assertNotEquals(originalItems[0].type, mappedItems[1].type)
        Assert.assertNotEquals(originalItems[0].title, mappedItems[1].title)
        Assert.assertNotEquals(originalItems[0].description, mappedItems[1].description)
    }
}