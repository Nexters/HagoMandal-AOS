package com.greedy0110.hagomandal.data.di

import android.content.Context
import androidx.room.Room
import com.greedy0110.hagomandal.BuildConfig
import com.greedy0110.hagomandal.data.db.AppDatabase
import com.greedy0110.hagomandal.data.db.GoalDao
import com.greedy0110.hagomandal.data.remote.api.HagoMandalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val CONNECT_TIMEOUT = 60.toLong()
const val WRITE_TIMEOUT = 60.toLong()
const val READ_TIMEOUT = 60.toLong()
const val BASE_URL = ""

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpCache(
        @ApplicationContext applicationContext: Context
    ): Cache {
        // 10MB
        val cacheSize = 10 * 1024 * 1024L
        return Cache(applicationContext.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        okHttpCache: Cache,
        tokenInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(okHttpCache)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): HagoMandalService {
        return retrofit.create(HagoMandalService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("$BASE_URL")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        val builder = if (BuildConfig.DEBUG) {
            Room.inMemoryDatabaseBuilder(applicationContext, AppDatabase::class.java)
        } else {
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, "HagoMandal-database")
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): GoalDao {
        return appDatabase.getDao()
    }
}
