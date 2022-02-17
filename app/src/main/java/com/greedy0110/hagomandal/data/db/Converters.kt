package com.greedy0110.hagomandal.data.db

import androidx.room.TypeConverter
import com.greedy0110.hagomandal.usecase.DetailGoal
import com.greedy0110.hagomandal.usecase.Job
import com.greedy0110.hagomandal.usecase.SubGoal
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {
    @TypeConverter
    fun fromJobToString(job: Job?): String? {
        val moshiBuilder = Moshi.Builder()
            .build()
        val adapter = moshiBuilder.adapter(Job::class.java)
        return adapter.toJson(job)
    }

    @TypeConverter
    fun fromStringToJob(value: String?): Job? {
        if (value == null) return null

        val moshiBuilder = Moshi.Builder()
            .build()
        val adapter = moshiBuilder.adapter(Job::class.java)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromSubGoalsToString(subGoals: List<SubGoal>?): String? {
        if (subGoals == null) return null

        val moshi = Moshi.Builder()
            .build()
        val detailGoalType = Types.newParameterizedType(
            MutableList::class.java,
            SubGoal::class.java
        )
        val adapter: JsonAdapter<List<SubGoal>> = moshi.adapter(detailGoalType)

        return adapter.toJson(subGoals)
    }

    @TypeConverter
    fun fromStringToSubGoals(value: String?): List<SubGoal>? {
        if (value == null) return null

        val moshi = Moshi.Builder()
            .build()
        val detailGoalType = Types.newParameterizedType(
            MutableList::class.java,
            SubGoal::class.java
        )
        val adapter: JsonAdapter<List<SubGoal>> = moshi.adapter(detailGoalType)

        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromDetailGoalToString(detailGoals: List<DetailGoal>?): String? {
        val moshi = Moshi.Builder()
            .build()
        val detailGoalType = Types.newParameterizedType(
            MutableList::class.java,
            DetailGoal::class.java
        )
        val adapter: JsonAdapter<List<DetailGoal>> = moshi.adapter(detailGoalType)

        return adapter.toJson(detailGoals)
    }

    @TypeConverter
    fun fromStringToDetailGoal(value: String?): List<DetailGoal>? {
        if (value == null) return null

        val moshi = Moshi.Builder()
            .build()
        val detailGoalType = Types.newParameterizedType(
            MutableList::class.java,
            DetailGoal::class.java
        )
        val adapter: JsonAdapter<List<DetailGoal>> = moshi.adapter(detailGoalType)
        return adapter.fromJson(value)
    }
}