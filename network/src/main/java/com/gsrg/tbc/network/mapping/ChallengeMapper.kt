package com.gsrg.tbc.network.mapping

import com.gsrg.tbc.core.models.Challenge
import com.gsrg.tbc.core.utils.Result
import com.gsrg.tbc.domain.model.ChallengeListResponse

class ChallengeMapper : Mapper<Result<ChallengeListResponse>, Result<List<Challenge>>>() {

    /**
     * Map from [Result] of [ChallengeListResponse] to [Result] of list of [Challenge]
     */
    override fun mapFrom(data: Result<ChallengeListResponse>): Result<List<Challenge>> {
        return when (data) {
            is Result.Success -> {
                return Result.Success(data = data.data.items.map {
                    Challenge(
                        id = it.id,
                        title = it.title,
                        description = it.description,
                        type = it.type,
                        goal = it.goal
                    )
                })
            }
            is Result.Error -> data
            is Result.Loading -> Result.Error(Exception("This should never happen"))
        }
    }
}