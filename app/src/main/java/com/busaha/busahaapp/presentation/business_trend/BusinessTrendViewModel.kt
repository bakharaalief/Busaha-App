package com.busaha.busahaapp.presentation.business_trend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busaha.busahaapp.domain.entity.Trend
import com.busaha.busahaapp.util.DataDummy

class BusinessTrendViewModel : ViewModel() {

    private val _listTrend = MutableLiveData<List<Trend>>()
    val listTrend: LiveData<List<Trend>> = _listTrend

    init {
        _listTrend.value = DataDummy.trendDummy()
    }
}