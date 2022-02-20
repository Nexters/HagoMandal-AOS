package com.greedy0110.hagomandal.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.greedy0110.hagomandal.usecase.DetailGoal
import com.greedy0110.hagomandal.usecase.SubGoal

data class MandalartIdEntity(val id: String)

@Dao
interface GoalDao {
    @Query("SELECT id FROM mandalart_entity WHERE userId = :userId LIMIT 1")
    fun getIncompleteMandalartId(userId: String): String?

    @Insert
    fun insertMandalart(mandalartEntity: MandalartEntity)

    @Query("INSERT INTO mandalart_entity(id, userId) VALUES(:mandalartId, :userId)")
    fun insertMandalartId(userId: String, mandalartId: String)

    @Query("UPDATE mandalart_entity SET mainGoal = :mainGoal WHERE id = :mandalartId")
    fun updateMainGoal(mandalartId: String, mainGoal: String)

    @Query("UPDATE mandalart_entity SET period = :period WHERE id = :mandalartId")
    fun updatePeriod(mandalartId: String, period: Int)

    @Query("SELECT mainGoal FROM mandalart_entity WHERE id = :mandalartId ")
    fun getMainGoal(mandalartId: String): String

    @Delete(entity = MandalartEntity::class)
    fun deleteMandlart(mandalartIdEntity: MandalartIdEntity)

    @Query("UPDATE mandalart_entity SET subGoals = :subGoals WHERE id = :mandalartId")
    fun updateSubGoals(mandalartId: String, subGoals: List<SubGoal>)

    @Query("SELECT subGoals FROM mandalart_entity WHERE id = :mandalartId")
    fun getSubGoals(mandalartId: String): List<SubGoal>?

    @Query("SELECT detailGoals FROM mandalart_entity WHERE id = :mandalartId")
    fun getDetailGoals(mandalartId: String): List<DetailGoal>?

    @Query("UPDATE mandalart_entity SET detailGoals = :detailGoals WHERE id = :mandalartId")
    fun updateDetailGoals(mandalartId: String, detailGoals: List<DetailGoal>)

    @Query("SELECT period FROM mandalart_entity WHERE id = :mandalartId")
    fun getPeriod(mandalartId: String): Int

    @Query("UPDATE mandalart_entity SET remoteId = :idAtRemote WHERE id = :mandalartId")
    fun updateRemoteMandalartId(mandalartId: String, idAtRemote: String)

    @Query("UPDATE mandalart_entity SET isComplete = :isComplete WHERE id = :mandalartId")
    fun updateIsComplete(mandalartId: String, isComplete: Boolean)

    @Query("SELECT remoteId FROM mandalart_entity WHERE id = :mandalartId")
    fun getIdAtRemote(mandalartId: String): String?
}
