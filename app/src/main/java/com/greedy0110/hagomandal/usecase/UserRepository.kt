package com.greedy0110.hagomandal.usecase

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(@ApplicationContext val context: Context) {
    private val sharedPreferences by lazy {
        context.getSharedPreferences("HagoMandal", Context.MODE_PRIVATE)
    }

    suspend fun getUserName(): String {
        return sharedPreferences.getString("name", null).orEmpty()
    }

    suspend fun setName(name: String) {
        sharedPreferences.edit {
            putString("name", name)
        }
    }

    suspend fun setJob(job: Job) {
        sharedPreferences.edit {
            putString("job", job.toString())
        }
    }

    suspend fun shownOnBoarding() {
        sharedPreferences.edit {
            putBoolean("shownOnBoarding", true)
        }
    }

    suspend fun isShownOnBoarding(): Boolean {
        return sharedPreferences.getBoolean("shownOnBoarding", false)
    }

    suspend fun getRemoteUserId(): String {
        // TODO: 구현
        return ""
    }
}
