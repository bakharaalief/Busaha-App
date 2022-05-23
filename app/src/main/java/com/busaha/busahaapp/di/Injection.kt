package com.busaha.busahaapp.di

import com.busaha.busahaapp.data.TrendRepository
import com.busaha.busahaapp.domain.repository.ITrendRepository
import com.busaha.busahaapp.domain.use_case.TrendInteract
import com.busaha.busahaapp.domain.use_case.TrendUseCase

object Injection {

    private fun provideTrendRepository(): ITrendRepository {
        return TrendRepository()
    }

    fun provideTrendUseCase(): TrendUseCase {
        val repository = provideTrendRepository()
        return TrendInteract(repository)
    }
}