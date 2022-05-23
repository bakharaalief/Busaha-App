package com.busaha.busahaapp.domain.use_case

import androidx.lifecycle.LiveData
import com.busaha.busahaapp.domain.entity.Trend
import com.busaha.busahaapp.domain.repository.ITrendRepository

class TrendInteract(private val trendRepository: ITrendRepository) : TrendUseCase {

    override fun getListTrend(): LiveData<List<Trend>> = trendRepository.getListTrend()
}