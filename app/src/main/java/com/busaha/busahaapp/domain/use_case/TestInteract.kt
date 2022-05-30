package com.busaha.busahaapp.domain.use_case

import androidx.lifecycle.LiveData
import com.busaha.busahaapp.data.Result
import com.busaha.busahaapp.domain.entity.Questions
import com.busaha.busahaapp.domain.entity.QuestionsOption
import com.busaha.busahaapp.domain.repository.ITestRepository

class TestInteract(private val testRepository: ITestRepository) : TestUseCase {

    override fun getTest(): LiveData<Result<Questions>> = testRepository.getTest()

    override fun getTestOption(questionId: Int): LiveData<Result<QuestionsOption>> =
        testRepository.getTestOption(questionId)
}