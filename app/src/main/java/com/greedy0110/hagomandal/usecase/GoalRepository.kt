package com.greedy0110.hagomandal.usecase

import com.greedy0110.hagomandal.data.db.GoalDao
import com.greedy0110.hagomandal.data.db.MandalartIdEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepository @Inject constructor(private val dao: GoalDao) {
    suspend fun initMandalart(userId: String): String {
        val id = RandomStringGenerator.generate(MANDALART_ID_LENGTH)
        dao.insertMandalartId(id, userId)
        return id
    }

    suspend fun deleteMandalart(id: String) {
        val mandalartIdEntity = MandalartIdEntity(id)
        dao.deleteMandlart(mandalartIdEntity)
    }

    suspend fun getIncompleteMandartId(userId: String): String? {
        return dao.getIncompleteMandalartId(userId)
    }

    suspend fun completeMandalart(mandalartId: String) {
        dao.updateIsComplete(mandalartId, true)
    }

    suspend fun getPeriod(mandalartId: String): Int {
        return dao.getPeriod(mandalartId)
    }

    suspend fun getMainGoal(mandalartId: String): String {
        return dao.getMainGoal(mandalartId)
    }

    suspend fun getSubGoals(mandalartId: String): List<SubGoal>? {
        return dao.getSubGoals(mandalartId)
    }

    suspend fun getDetailGoals(mandalartId: String): List<DetailGoal>? {
        return dao.getDetailGoals(mandalartId)
    }

    suspend fun getRemoteMandalartId(mandalartId: String): String? {
        return dao.getIdAtRemote(mandalartId)
    }

    suspend fun setMainGoal(mandalartId: String, goal: String) {
        dao.updateMainGoal(mandalartId, goal)
    }

    suspend fun setPeriod(mandalartId: String, period: Int) {
        dao.updatePeriod(mandalartId, period)
    }

    suspend fun setRemoteMandalartId(mandalartId: String, idAtRemote: String) {
        dao.updateRemoteMandalartId(mandalartId, idAtRemote)
    }

    suspend fun setSubGoal(mandalartId: String, subGoalId: Int, goal: String, color: GoalColor) {
        dao.getSubGoals(mandalartId)?.let {
            val subGoals = it.toMutableList()
            subGoals[subGoalId] = SubGoal(color, goal)
            dao.updateSubGoals(mandalartId, subGoals)
        } ?: throw IllegalStateException("initMandalart를 먼저 호출해야 합니다")
    }

    suspend fun setDetailGoal(mandalartId: String, detailGoalId: Int, goal: String) {
        dao.getDetailGoals(mandalartId)?.let {
            val detailGoals = it.toMutableList()
            detailGoals[detailGoalId] = DetailGoal(goal)
            dao.updateDetailGoals(mandalartId, detailGoals)
        } ?: throw IllegalStateException("initMandalart를 먼저 호출해야 합니다")
    }

    companion object {
        private const val MANDALART_ID_LENGTH = 16
    }
}
