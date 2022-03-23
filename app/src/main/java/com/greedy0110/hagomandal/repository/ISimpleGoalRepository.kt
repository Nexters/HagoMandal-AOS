package com.greedy0110.hagomandal.repository

import android.content.Context
import androidx.core.content.edit
import com.greedy0110.hagomandal.ui.DetailGoal
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GaolModule {

    @Binds
    abstract fun bindsISimpleGoalRepository(simpleGoalRepository: SimpleGoalRepository): ISimpleGoalRepository
}

interface ISimpleGoalRepository {
    suspend fun saveGoals(main: String, details: List<DetailGoal>)
    suspend fun getGoals(): Pair<String, List<DetailGoal>>
}

@Singleton
class SimpleGoalRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val moshi: Moshi,
) : ISimpleGoalRepository {

    private val preference by lazy { appContext.getSharedPreferences(NAME, Context.MODE_PRIVATE) }

    private val detailJsonAdapter by lazy {
        val type = Types.newParameterizedType(List::class.java, DetailGoal::class.java)
        val jsonAdapter = moshi.adapter<List<DetailGoal>>(type)
        jsonAdapter
    }

    override suspend fun saveGoals(main: String, details: List<DetailGoal>) {
        withContext(Dispatchers.IO) {
            preference.edit {
                val detailsJson = detailJsonAdapter.toJson(details)

                putString(MAIN, main)
                putString(DETAILS, detailsJson)
                Timber.d("목표 저장 완료 $main $details")
            }
        }
    }

    override suspend fun getGoals(): Pair<String, List<DetailGoal>> {
        return withContext(Dispatchers.IO) {
            val main = preference.getString(MAIN, null) ?: throw RuntimeException("1")
            val details: List<DetailGoal> = preference.getString(DETAILS, null)
                ?.let { json -> detailJsonAdapter.fromJson(json) }
                ?: throw RuntimeException("2")

            Pair(main, details)
        }
    }

    companion object {
        private const val NAME = "SimpleGoal"
        private const val MAIN = "main"
        private const val DETAILS = "details"
    }
}
