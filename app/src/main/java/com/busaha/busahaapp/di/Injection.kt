package com.busaha.busahaapp.di

import android.content.Context
import com.busaha.busahaapp.data.TestRepository
import com.busaha.busahaapp.data.TrendRepository
import com.busaha.busahaapp.data.UserRepository
import com.busaha.busahaapp.data.local.room.AnswerDB
import com.busaha.busahaapp.data.remote.retorift.ApiConfig
import com.busaha.busahaapp.domain.repository.ITestRepository
import com.busaha.busahaapp.domain.repository.ITrendRepository
import com.busaha.busahaapp.domain.repository.IUserRepository
import com.busaha.busahaapp.domain.use_case.*

object Injection {

    private fun provideTrendRepository(): ITrendRepository {
        return TrendRepository()
    }

    fun provideTrendUseCase(): TrendUseCase {
        val repository = provideTrendRepository()
        return TrendInteract(repository)
    }

    private fun provideUserRepository(): IUserRepository {
        val apiConfig = ApiConfig.getApiService()
        return UserRepository(apiConfig)
    }

    fun provideUserUseCase(): UserUseCase {
        val repository = provideUserRepository()
        return UserInteract(repository)
    }

    private fun provideTestRepository(context: Context): ITestRepository {
        val apiConfig = ApiConfig.getApiService()
        val database = AnswerDB.getInstance(context)
        val dao = database.answerDao()
        return TestRepository(apiConfig, dao)
    }

    fun provideTestUseCase(context: Context): TestUseCase {
        val repository = provideTestRepository(context)
        return TestInteract(repository)
    }
}