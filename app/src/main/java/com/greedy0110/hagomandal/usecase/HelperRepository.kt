package com.greedy0110.hagomandal.usecase

import javax.inject.Singleton

@Singleton
class HelperRepository {
    suspend fun setMainGoalRecommendationBase() {
        // TODO("Not Implemented")
    }

    suspend fun setSubGoalRecommendationBase(index: Int, subGoal: String) {
        // TODO("Not Implemented")
    }

    suspend fun setDetailGoalRecommendationBase(
        subGoalIndex: Int,
        detailGoalIndex: Int,
        detailGaol: String
    ) {
        // TODO("Not Implemented")
    }

    suspend fun getSubGoalRecommendations(): List<String> {
        // TODO("Not Implemented")
        return listOf("sub goal recommendations")
    }

    suspend fun getDetailGoalRecommendations(): List<String> {
        // TODO("Not Implemented")
        return listOf("detail goal recommendations")
    }
}
