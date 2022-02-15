package com.greedy0110.hagomandal.usecase

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(@ApplicationContext val context: Context) {
    private var name: String = ""
    private var job: Job? = null
    private val sharedPreferences by lazy {
        context.getSharedPreferences("HagoMandal", Context.MODE_PRIVATE)
    }

    suspend fun setName(name: String) {
        sharedPreferences.edit {
            putString("name", name)
        }
    }

    suspend fun setJob(job: Job) {
        sharedPreferences.edit {
            putString("name", job.toString())
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
}

