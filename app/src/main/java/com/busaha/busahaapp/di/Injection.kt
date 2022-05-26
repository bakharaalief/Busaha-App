package com.busaha.busahaapp.di

import com.busaha.busahaapp.data.TrendRepository
import com.busaha.busahaapp.data.UserRepository
import com.busaha.busahaapp.data.remote.retorift.ApiConfig
import com.busaha.busahaapp.domain.repository.ITrendRepository
import com.busaha.busahaapp.domain.repository.IUserRepository
import com.busaha.busahaapp.domain.use_case.TrendInteract
import com.busaha.busahaapp.domain.use_case.TrendUseCase
import com.busaha.busahaapp.domain.use_case.UserInteract
import com.busaha.busahaapp.domain.use_case.UserUseCase

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
}