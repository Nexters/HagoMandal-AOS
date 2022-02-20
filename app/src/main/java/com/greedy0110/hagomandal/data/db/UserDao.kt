package com.greedy0110.hagomandal.data.db

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface UserDao {
    @Insert
    fun insertUser(userEntity: UserEntity)
}
