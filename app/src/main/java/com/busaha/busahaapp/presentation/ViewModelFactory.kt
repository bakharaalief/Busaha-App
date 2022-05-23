package com.busaha.busahaapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.busaha.busahaapp.di.Injection
import com.busaha.busahaapp.domain.use_case.TrendUseCase
import com.busaha.busahaapp.presentation.business_trend.BusinessTrendViewModel

class ViewModelFactory(private val trendUseCase: TrendUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(BusinessTrendViewModel::class.java) -> BusinessTrendViewModel(
                trendUseCase
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideTrendUseCase())
            }.also { instance = it }
    }
}