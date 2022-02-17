package com.greedy0110.hagomandal.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.greedy0110.hagomandal.usecase.GoalColor

@Dao
interface GoalDao {
    @Insert
    fun insertMandalart(mandalartEntity: MandalartEntity)
}
