package com.busaha.busahaapp.domain.repository

import androidx.lifecycle.LiveData
import com.busaha.busahaapp.domain.entity.Trend

interface ITrendRepository {
    fun getListTrend(): LiveData<List<Trend>>
}