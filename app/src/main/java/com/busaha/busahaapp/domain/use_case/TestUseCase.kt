package com.busaha.busahaapp.domain.use_case

import androidx.lifecycle.LiveData
import com.busaha.busahaapp.data.Result
import com.busaha.busahaapp.domain.entity.Questions
import com.busaha.busahaapp.domain.entity.QuestionsOption

interface TestUseCase {
    fun getTest(): LiveData<Result<Questions>>

    fun getTestOption(questionId: Int): LiveData<Result<QuestionsOption>>
}