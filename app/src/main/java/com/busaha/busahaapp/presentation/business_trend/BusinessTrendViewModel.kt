package com.busaha.busahaapp.presentation.business_trend

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.busaha.busahaapp.domain.entity.Trend
import com.busaha.busahaapp.domain.use_case.TrendUseCase

class BusinessTrendViewModel(trendUseCase: TrendUseCase) : ViewModel() {

    val listTrend: LiveData<List<Trend>> = trendUseCase.getListTrend()
}