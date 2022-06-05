package com.busaha.busahaapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.busaha.busahaapp.domain.model.Trend
import com.busaha.busahaapp.domain.repository.ITrendRepository
import com.busaha.busahaapp.util.DataDummy

class TrendRepository : ITrendRepository {

    override fun getListTrend(): LiveData<List<Trend>> {
        val result = MutableLiveData<List<Trend>>()
        result.value = DataDummy.trendDummy()
        return result
    }

}