package com.busaha.busahaapp.domain.use_case

import androidx.lifecycle.LiveData
import com.busaha.busahaapp.domain.entity.Trend

interface TrendUseCase {
    fun getListTrend(): LiveData<List<Trend>>
}