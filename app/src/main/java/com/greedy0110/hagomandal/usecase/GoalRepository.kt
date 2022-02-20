package com.greedy0110.hagomandal.usecase

import com.greedy0110.hagomandal.data.db.GoalDao
import com.greedy0110.hagomandal.data.db.MandalartIdEntity
import com.greedy0110.hagomandal.data.remote.GoalCategoryDto.DETAIL_GOAL_CATEGORY
import com.greedy0110.hagomandal.data.remote.GoalCategoryDto.MAIN_GOAL_CATEGORY
import com.greedy0110.hagomandal.data.remote.GoalCategoryDto.SUB_GOAL_CATEGORY
import com.greedy0110.hagomandal.data.remote.GoalDto
import com.greedy0110.hagomandal.data.remote.api.HagoMandalService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepository @Inject constructor(
    private val dao: GoalDao,
    private val service: HagoMandalService
) {
    suspend fun initMandalart(userId: String): String {
        val id = RandomStringGenerator.generate(MANDALART_ID_LENGTH)
        dao.insertMandalartId(userId = userId, mandalartId = id)

        val mandalartIdDto = service.postMandalart()
        dao.insertRemoteMandalartId(mandalartId = id, remoteId = mandalartIdDto.remoteMandalartId)
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
        return dao.getRemoteMandalartId(mandalartId)
    }

    suspend fun setMainGoal(mandalartId: String, goal: String) {
        dao.updateMainGoal(mandalartId, goal)
        val remoteMandalartId = dao.getRemoteMandalartId(mandalartId)
            ?: throw IllegalStateException("GoalRepository의 initMandalart()를 먼저 호출해야 합니다.")

        val goalDto = GoalDto(
            goalCategory = MAIN_GOAL_CATEGORY,
            position = 0,
            title = goal
        )
        service.postGoal(remoteMandalartId, goalDto)
    }

    suspend fun setPeriod(mandalartId: String, period: Int) {
        dao.updatePeriod(mandalartId, period)
    }

    suspend fun setRemoteMandalartId(mandalartId: String, idAtRemote: String) {
        dao.updateRemoteMandalartId(mandalartId, idAtRemote)
    }

    suspend fun setSubGoal(mandalartId: String, subGoalId: Int, goal: String, color: GoalColor) {
        val subGoals = dao.getSubGoals(mandalartId)
            ?: throw IllegalStateException("GoalRepository의 initMandalart()를 먼저 호출해야 합니다.")
        val mutableSubGoals = subGoals.toMutableList()

        mutableSubGoals[subGoalId] = SubGoal(color, goal)
        dao.updateSubGoals(mandalartId, mutableSubGoals)

        val remoteGoalId = dao.getRemoteMandalartId(mandalartId)
            ?: throw IllegalStateException("GoalRepository의 initMandalart()를 먼저 호출해야 합니다.")

        val goalDto = GoalDto(
            goalCategory = SUB_GOAL_CATEGORY,
            position = subGoalId,
            title = goal
        )
        service.postGoal(remoteGoalId, goalDto)
    }

    suspend fun setDetailGoal(mandalartId: String, detailGoalId: Int, goal: String) {
        val detailGoals = dao.getDetailGoals(mandalartId)
            ?: throw IllegalStateException("GoalRepository의 initMandalart()를 먼저 호출해야 합니다.")

        val mutableDetailGoals = detailGoals.toMutableList()
        mutableDetailGoals[detailGoalId] = DetailGoal(goal)
        dao.updateDetailGoals(mandalartId, mutableDetailGoals)

        val remoteMandalartId = dao.getRemoteMandalartId(mandalartId)
            ?: throw IllegalStateException("GoalRepository의 initMandalart()를 먼저 호출해야 합니다.")
        val goalDto = GoalDto(
            goalCategory = DETAIL_GOAL_CATEGORY,
            position = detailGoalId,
            title = goal
        )
        service.postGoal(remoteMandalartId, goalDto)
    }

    companion object {
        private const val MANDALART_ID_LENGTH = 16
    }
}
