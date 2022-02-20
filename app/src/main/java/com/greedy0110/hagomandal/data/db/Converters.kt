package com.greedy0110.hagomandal.data.db

import androidx.room.TypeConverter
import com.greedy0110.hagomandal.ui.SubGaolScreen
import com.greedy0110.hagomandal.usecase.DetailGoal
import com.greedy0110.hagomandal.usecase.GoalColor
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
    fun fromSubGoalToString(subGoal: SubGoal?): String? {
        val moshi = Moshi.Builder()
            .build()
        val adapter = moshi.adapter(SubGoal::class.java)
        return adapter.toJson(subGoal)
    }

    @TypeConverter
    fun fromStringToSubGoal(value: String?): SubGoal? {
        if (value == null) return null
        val moshi = Moshi.Builder()
            .build()
        val adapter = moshi.adapter(SubGoal::class.java)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromSubGoalsToString(subGoals: List<SubGoal>?): String? {
        val moshi = Moshi.Builder()
            .build()
        val detailGoalType = Types.newParameterizedType(
            List::class.java,
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
            List::class.java,
            SubGoal::class.java
        )
        val adapter: JsonAdapter<List<SubGoal>> = moshi.adapter(detailGoalType)

        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromDetailGoalsToString(detailGoals: List<DetailGoal>?): String? {
        val moshi = Moshi.Builder()
            .build()
        val detailGoalType = Types.newParameterizedType(
            List::class.java,
            DetailGoal::class.java
        )
        val adapter: JsonAdapter<List<DetailGoal>> = moshi.adapter(detailGoalType)

        return adapter.toJson(detailGoals)
    }

    @TypeConverter
    fun fromStringToDetailGoals(value: String?): List<DetailGoal>? {
        if (value == null) return null

        val moshi = Moshi.Builder()
            .build()
        val detailGoalType = Types.newParameterizedType(
            List::class.java,
            DetailGoal::class.java
        )
        val adapter: JsonAdapter<List<DetailGoal>> = moshi.adapter(detailGoalType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromDetailGoalToString(detailGoal: DetailGoal?): String? {
        val moshi = Moshi.Builder()
            .build()
        val adapter = moshi.adapter(DetailGoal::class.java)
        return adapter.toJson(detailGoal)
    }

    @TypeConverter
    fun fromStringToDetailGoal(value: String?): DetailGoal? {
        if (value == null) return null

        val moshi = Moshi.Builder()
            .build()
        val adapter = moshi.adapter(DetailGoal::class.java)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromColoToString(color: GoalColor?): String? {
        val moshi = Moshi.Builder()
            .build()
        return moshi.adapter(GoalColor::class.java)
            .toJson(color)
    }

    @TypeConverter
    fun fromStringToColor(value: String?): GoalColor? {
        if (value == null) return null

        val moshi = Moshi.Builder()
            .build()
        return moshi.adapter(GoalColor::class.java)
            .fromJson(value)
    }
}
