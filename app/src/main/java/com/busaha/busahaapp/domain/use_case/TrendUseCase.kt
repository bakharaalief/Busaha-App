package com.busaha.busahaapp.domain.use_case

import androidx.lifecycle.LiveData
import com.busaha.busahaapp.domain.model.Trend

interface TrendUseCase {
    fun getListTrend(): LiveData<List<Trend>>
}