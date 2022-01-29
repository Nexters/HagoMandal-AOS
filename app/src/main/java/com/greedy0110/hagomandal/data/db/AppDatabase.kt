package com.greedy0110.hagomandal.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GoalEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun goalDao(): GoalDao
}