package com.greedy0110.hagomandal.usecase

import com.greedy0110.hagomandal.data.remote.GoalCategoryDto
import com.greedy0110.hagomandal.data.remote.api.HagoMandalService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HelperRepository @Inject constructor(private val service: HagoMandalService) {

    suspend fun getSubGoalRecommendations(): List<String> {
        val hintListDto =
            service.getGoalRecommendations(goalCategory = GoalCategoryDto.SUB_GOAL_CATEGORY)
        return hintListDto.hints
            .map { it.title }
    }

    suspend fun getDetailGoalRecommendations(): List<String> {
        val hintListDto =
            service.getGoalRecommendations(goalCategory = GoalCategoryDto.DETAIL_GOAL_CATEGORY)
        return hintListDto.hints
            .map { it.title }
    }
}
