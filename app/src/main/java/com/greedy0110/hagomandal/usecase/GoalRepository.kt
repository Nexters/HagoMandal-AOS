package com.greedy0110.hagomandal.usecase

import com.greedy0110.hagomandal.data.db.GoalDao
import com.greedy0110.hagomandal.data.db.MandalartEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepository @Inject constructor(private val dao: GoalDao) {
    fun setGoal(
        id: String,
        period: Int,
        mainGoal: String,
        subGoals: List<SubGoal>,
        detailGoal: List<DetailGoal>
    ) {
        val mandalartEntity = MandalartEntity(id, period, mainGoal, subGoals, detailGoal)
        dao.insertMandalart(mandalartEntity)
    }
}
