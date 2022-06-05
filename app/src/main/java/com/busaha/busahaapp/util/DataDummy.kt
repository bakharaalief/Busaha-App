package com.busaha.busahaapp.util

import com.busaha.busahaapp.domain.model.Trend

object DataDummy {
    fun trendDummy(): List<Trend> {
        val result = ArrayList<Trend>()

        for (i in 0..10) {
            result.add(
                Trend(
                    id = 0,
                    title = "Ternak Lele $i",
                    desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Odio at viverra venenatis pellentesque dignissim sed tellus amet. Amet, adipiscing nam nunc turpis in"
                )
            )
        }

        return result
    }
}